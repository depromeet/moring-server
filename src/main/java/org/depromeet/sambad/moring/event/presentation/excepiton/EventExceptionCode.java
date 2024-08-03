package org.depromeet.sambad.moring.event.presentation.excepiton;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.depromeet.sambad.moring.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventExceptionCode implements ExceptionCode {

	NOT_FOUND_EVENT(NOT_FOUND, "이벤트가 존재하지 않습니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}