package org.depromeet.sambad.moring.file.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record FileUrlResponse(
	@Schema(description = "파일 URL", example = "https://example.com", requiredMode = REQUIRED)
	String url
) {
	public static FileUrlResponse of(String url) {
		return new FileUrlResponse(url);
	}
}