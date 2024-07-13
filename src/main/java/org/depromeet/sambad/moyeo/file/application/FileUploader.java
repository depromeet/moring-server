package org.depromeet.sambad.moyeo.file.application;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FileUploader {

	String upload(MultipartFile file, String originalFileName) throws IOException;

	String upload(String fileUrl) throws IOException;

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
