package org.depromeet.sambad.moyeo.file.presentation;

import java.io.IOException;

import org.depromeet.sambad.moyeo.file.application.FileService;
import org.depromeet.sambad.moyeo.file.presentation.response.FileUrlResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/object-storage")
public class ObjectStorageController {

	private final FileService fileService;

	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<FileUrlResponse> uploadPetImage(
		@RequestPart(value = "fileName") String fileName,
		@RequestPart(value = "file", required = false) MultipartFile multipartFile
	) throws IOException {
		FileUrlResponse response = fileService.upload(fileName, multipartFile);
		return ResponseEntity.ok(response);
	}



}
