package org.depromeet.sambad.moyeo.common.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalExceptionCode implements ExceptionCode {
	INVALID_INPUT(BAD_REQUEST, "유효한 입력 형식이 아닙니다."),

	SERVER_ERROR(INTERNAL_SERVER_ERROR, "예상치 못한 문제가 발생했습니다.");

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
