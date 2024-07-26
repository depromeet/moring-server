package org.depromeet.sambad.moring.file.application;

import java.io.IOException;

import org.depromeet.sambad.moring.file.domain.FileEntity;
import org.depromeet.sambad.moring.file.domain.FileRepository;
import org.depromeet.sambad.moring.file.presentation.exception.NotFoundFileException;
import org.depromeet.sambad.moring.file.presentation.response.FileUrlResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FileService {

	private final FileUploader fileUploader;
	private final FileRepository fileRepository;

	@Transactional
	public FileUrlResponse upload(MultipartFile multipartFile) throws IOException {
		String url = fileUploader.upload(multipartFile, multipartFile.getOriginalFilename());
		FileEntity fileEntity = FileEntity.of(multipartFile.getOriginalFilename(), url);
		fileRepository.save(fileEntity);
		return FileUrlResponse.of(fileEntity.getPhysicalPath());
	}

	@Transactional
	public void delete(Long fileId) {
		if (isNotExistFile(fileId)) {
			throw new NotFoundFileException();
		}
		fileRepository.deleteById(fileId);
		fileUploader.delete(fileId);
	}

	@Transactional
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

	private boolean isNotExistFile(Long fileId) {
		return !fileRepository.existsById(fileId);
	}
}
