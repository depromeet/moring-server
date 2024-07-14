package org.depromeet.sambad.moyeo.file.presentation.response;

public record FileUrlResponse(
	String url
) {
	public static FileUrlResponse of(String url) {
		return new FileUrlResponse(url);
	}
}
