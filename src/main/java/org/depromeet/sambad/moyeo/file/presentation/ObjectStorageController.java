package org.depromeet.sambad.moyeo.file.presentation;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.depromeet.sambad.moyeo.file.application.FileService;
import org.depromeet.sambad.moyeo.file.presentation.response.FileUrlResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/object-storage")
@Tag(name = "ObjectStorage", description = "파일 저장소")
public class ObjectStorageController {

	private final FileService fileService;

	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(
		summary = "[U] 파일 업로드",
		description = "파일을 업로드합니다.",
		parameters = {
			@Parameter(name = "fileName", description = "파일 이름", required = true),
			@Parameter(name = "file", description = "파일", required = true)
		}
	)
	public ResponseEntity<FileUrlResponse> uploadFile(
		@RequestPart(value = "fileName") String fileName,
		@RequestPart(value = "file", required = false) MultipartFile multipartFile
	) throws IOException {
		FileUrlResponse response = fileService.upload(fileName, multipartFile);
		return ResponseEntity.ok(response);
	}

	@GetMapping(path = "/download/{fileName}")
	@Operation(
		summary = "[U] 파일 다운로드",
		description = "파일을 다운로드합니다.",
		parameters = {
			@Parameter(name = "fileName", description = "파일 이름", required = true)
		}
	)
	public ResponseEntity<byte[]> downloadFile(
		@PathVariable String fileName
	) {
		byte[] file = fileService.download(fileName);
		String downloadedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.IMAGE_JPEG);
		httpHeaders.setContentLength(file.length);
		httpHeaders.setContentDispositionFormData("attachment", downloadedFileName);
		return new ResponseEntity<byte[]>(file, httpHeaders, HttpStatus.OK);
	}

	@DeleteMapping(path = "/delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(
		summary = "[U] 파일 삭제",
		description = "파일을 삭제합니다.",
		parameters = {
			@Parameter(name = "fileName", description = "파일 이름", required = true)
		}
	)
	public ResponseEntity<Void> deleteFile(
		@RequestPart(value = "fileName") String fileName
	) {
		fileService.delete(fileName);
		return ResponseEntity.noContent().build();
	}

}
