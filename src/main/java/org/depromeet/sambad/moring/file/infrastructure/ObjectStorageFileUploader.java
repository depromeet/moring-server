package org.depromeet.sambad.moring.file.infrastructure;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import org.depromeet.sambad.moring.file.application.FileUploader;
import org.depromeet.sambad.moring.file.domain.FileEntity;
import org.depromeet.sambad.moring.file.domain.FileRepository;
import org.depromeet.sambad.moring.file.presentation.exception.ObjectStorageServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Profile({"dev", "prod"})
@Slf4j
@Service
@RequiredArgsConstructor
public class ObjectStorageFileUploader implements FileUploader {

	@Value("${cloud.ncp.object-storage.credentials.bucket}")
	private String bucketName;

	private final AmazonS3 amazonS3;
	private final FileRepository fileRepository;
	private final FileProperties fileProperties;

	@Override
	public void delete(Long Id) {
		FileEntity fileEntity = fileRepository.findById(Id);
		try {
			amazonS3.deleteObject(bucketName, fileEntity.getLogicalName());
		} catch (ObjectStorageServerException e) {
			throw new ObjectStorageServerException();
		}
	}

	@Override
	public String upload(MultipartFile multipartFile, String logicalName) throws IOException {
		try {
			File uploadFile = convert(multipartFile)
				.orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File convert failed."));
			String filePath = generateUploadPath(logicalName);
			amazonS3.putObject(
				new PutObjectRequest(bucketName, filePath, uploadFile)
					.withCannedAcl(CannedAccessControlList.PublicRead)
			);
			removeNewFile(uploadFile);
			return filePath;
		} catch (ObjectStorageServerException e) {
			throw new ObjectStorageServerException();
		}
	}

	@Override
	public String upload(String fileUrl) throws ObjectStorageServerException {
		try {
			String filePath = generateUploadPath(fileUrl);
			amazonS3.putObject(bucketName, filePath, fileUrl);
			return filePath;
		} catch (ObjectStorageServerException e) {
			throw new ObjectStorageServerException();
		}
	}

	private Optional<File> convert(MultipartFile file) throws IOException {
		File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
		if (convertFile.createNewFile()) {
			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
				fos.write(file.getBytes());
			}
			return Optional.of(convertFile);
		}
		return Optional.empty();
	}

	private void removeNewFile(File targetFile) {
		if (targetFile.delete()) {
			log.info("File deleted.");
		} else {
			log.info("File could not deleted");
		}
	}

	private String generateUploadPath(String originalFileName) {
		String fileExtension = getFileExtension(originalFileName);
		String fileName = generateUniqueFileName(fileExtension);

		return Path.of(fileProperties.uploadPath(), fileName).toString();
	}
}
