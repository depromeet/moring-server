package org.depromeet.sambad.moyeo.file.application;

import java.io.IOException;

import org.depromeet.sambad.moyeo.file.domain.FileEntity;
import org.depromeet.sambad.moyeo.file.domain.FileRepository;
import org.depromeet.sambad.moyeo.file.infrastructure.ObjectStorageFileUploader;
import org.depromeet.sambad.moyeo.file.presentation.exception.NotFoundFileException;
import org.depromeet.sambad.moyeo.file.presentation.response.FileUrlResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class FileService {

	private final FileUploader fileUploader;
	private final FileRepository fileRepository;
	private final ObjectStorageFileUploader objectStorageFileUploader;

	@Transactional
	public FileUrlResponse upload(String logicalName, MultipartFile multipartFile) throws IOException {
		String url = objectStorageFileUploader.upload(multipartFile, logicalName);
		FileEntity fileEntity = FileEntity.of(logicalName, url);
		fileRepository.save(fileEntity);
		return FileUrlResponse.of(url);
	}

	@Transactional
	public void delete(String logicalName) {
		if(isExistFile(logicalName)) {
			throw new NotFoundFileException();
		}
		fileRepository.deleteByLogicalName(logicalName);
		objectStorageFileUploader.delete(logicalName);
	}

	public byte[] download(String logicalName) {
		if(isExistFile(logicalName)) {
			throw new NotFoundFileException();
		}
		return objectStorageFileUploader.download(logicalName);
	}

	public FileEntity uploadAndSave(String fileUrl) {
		// TODO: add url validation
		try {
			String uploadedPath = fileUploader.upload(fileUrl);
			FileEntity fileEntity = FileEntity.of(fileUploader.getFileNameFromUrl(fileUrl), uploadedPath);
			return fileRepository.save(fileEntity);
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload file", e);
		}
	}

	private boolean isExistFile(String logicalName) {
		return !fileRepository.existsByLogicalName(logicalName);
	}

}
