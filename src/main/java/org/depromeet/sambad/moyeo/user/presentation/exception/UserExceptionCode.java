package org.depromeet.sambad.moyeo.user.presentation.exception;

import static org.springframework.http.HttpStatus.*;

import org.depromeet.sambad.moyeo.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserExceptionCode implements ExceptionCode {
	NOT_FOUND_USER(NOT_FOUND, "등록된 사용자가 없습니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
