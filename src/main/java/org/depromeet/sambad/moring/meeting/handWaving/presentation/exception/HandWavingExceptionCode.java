package org.depromeet.sambad.moring.meeting.handWaving.presentation.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.depromeet.sambad.moring.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HandWavingExceptionCode implements ExceptionCode {

	NOT_FOUND_HAND_WAVING(NOT_FOUND, "손 흔들기 내역을 찾을 수 없습니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
