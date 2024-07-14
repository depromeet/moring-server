package org.depromeet.sambad.moyeo.file.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.depromeet.sambad.moyeo.file.domain.FileEntity;
import org.depromeet.sambad.moyeo.file.domain.FileRepository;
import org.depromeet.sambad.moyeo.file.infrastructure.ObjectStorageFileUploader;
import org.depromeet.sambad.moyeo.file.presentation.response.FileUrlResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class FileService {

	private final FileUploader fileUploader;
	private final FileRepository fileRepository;
	private final ObjectStorageFileUploader objectStorageFileUploader;

	public FileUrlResponse upload(String logicalName, MultipartFile multipartFile) throws IOException {
		String url = objectStorageFileUploader.upload(multipartFile, logicalName);
		return FileUrlResponse.of(url);
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

}
