package org.depromeet.sambad.moring.file.application;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {

	String upload(MultipartFile file, String originalFileName) throws IOException;

	String upload(String fileUrl) throws IOException;

	void delete(Long Id);

	default String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
	}

	default String generateUniqueFileName(String fileExtension) {
		return UUID.randomUUID() + fileExtension;
	}

	default String getFileNameFromUrl(String fileUrl) {
		return fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
	}
}
