package org.depromeet.sambad.moring.domain.file.infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.depromeet.sambad.moring.domain.file.application.FileUploader;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Profile("local")
@RequiredArgsConstructor
@Service
public class LocalFileUploader implements FileUploader {

	private final FileProperties fileProperties;

	@Override
	public String upload(MultipartFile file, String originalFileName) throws IOException {
		Path uploadPath = generateUploadPath(originalFileName);

		return Files.write(uploadPath, file.getBytes())
			.toString();
	}

	@Override
	public String upload(String fileUrl) throws IOException {
		String originalFileName = getFileNameFromUrl(fileUrl);
		Path uploadPath = generateUploadPath(originalFileName);

		try (InputStream in = new URL(fileUrl).openStream()) {
			Files.copy(in, uploadPath, StandardCopyOption.REPLACE_EXISTING);
		}

		return uploadPath.toString();
	}

	@Override
	public void delete(Long Id) {
		// TODO: 구현
	}

	private Path generateUploadPath(String originalFileName) throws IOException {
		String fileExtension = getFileExtension(originalFileName);
		String fileName = generateUniqueFileName(fileExtension);

		Path uploadPath = Path.of(fileProperties.uploadPath(), fileName);
		Files.createDirectories(uploadPath.getParent());
		return uploadPath;
	}
}
