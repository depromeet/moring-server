package org.depromeet.sambad.moring.meeting.handwaving.presentation.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.depromeet.sambad.moring.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HandWavingExceptionCode implements ExceptionCode {

	INVALID_HAND_WAVING_STATUS_CHANGE(BAD_REQUEST, "이미 인사를 건냈거나 모른척 한 상태입니다."),

	INVALID_HAND_WAVING_RECEIVER(FORBIDDEN, "나도 인사 건내기, 모른척하기 권한이 없습니다."),

	NOT_FOUND_HAND_WAVING(NOT_FOUND, "손 흔들기 내역을 찾을 수 없습니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
