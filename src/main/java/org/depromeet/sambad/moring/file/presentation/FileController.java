package org.depromeet.sambad.moring.file.presentation;

import java.io.IOException;

import org.depromeet.sambad.moring.file.application.FileService;
import org.depromeet.sambad.moring.file.presentation.response.FileUrlResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "file", description = "파일 업로드 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/files")
public class FileController {

	private final FileService fileService;

	@Operation(summary = "파일 업로드", description = "파일을 NCP Object Storage 버킷에 업로드합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			content = @Content(schema = @Schema(implementation = Object.class))),
		@ApiResponse(responseCode = "415", description = "UNSUPPORTED_FILE_TYPE"),
		@ApiResponse(responseCode = "500", description = "OBJECTSTORAGE_SERVER_ERROR"),
	})
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<FileUrlResponse> uploadFile(
		@RequestPart(value = "file", required = false) MultipartFile multipartFile
	) throws IOException {
		FileUrlResponse response = fileService.upload(multipartFile);
		return ResponseEntity.ok(response);
	}

}
