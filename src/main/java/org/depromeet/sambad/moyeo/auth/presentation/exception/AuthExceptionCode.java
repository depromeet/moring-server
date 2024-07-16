package org.depromeet.sambad.moyeo.auth.presentation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moyeo.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionCode implements ExceptionCode {
	AUTHENTICATION_REQUIRED(HttpStatus.UNAUTHORIZED, "인증 정보가 유효하지 않습니다."),
	ALREADY_REGISTERED_USER(HttpStatus.BAD_REQUEST, "다른 소셜 계정으로 이미 가입된 사용자입니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
