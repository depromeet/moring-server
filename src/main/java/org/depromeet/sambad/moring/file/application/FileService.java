package org.depromeet.sambad.moring.file.application;

import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moring.file.domain.FileEntity;
import org.depromeet.sambad.moring.file.domain.FileRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class FileService {

	private final FileUploader fileUploader;
	private final FileRepository fileRepository;

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
