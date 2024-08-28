package org.depromeet.sambad.moring.domain.file.presentation.exception;

import static org.springframework.http.HttpStatus.*;

import org.depromeet.sambad.moring.domain.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileExceptionCode implements ExceptionCode {
	NOT_FOUND_FILE(NOT_FOUND, "파일이 존재하지 않습니다."),

	EXCEED_FILE_SIZE(PAYLOAD_TOO_LARGE, "파일 용량이 초과되었습니다."),
	EXCEED_FILE_COUNT(PAYLOAD_TOO_LARGE, "파일 개수가 초과되었습니다."),

	UNSUPPORTED_FILE_TYPE(UNSUPPORTED_MEDIA_TYPE, "파일 형식은 '.jpg', '.jpeg', '.png' 만 가능합니다."),

	OBJECTSTORAGE_SERVER_ERROR(INTERNAL_SERVER_ERROR, "파일 처리 중 서버 에러가 발생했습니다.");

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
