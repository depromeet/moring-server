package org.depromeet.sambad.moring.meeting.meeting.presentation.exception;

import static org.springframework.http.HttpStatus.*;

import org.depromeet.sambad.moring.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeetingExceptionCode implements ExceptionCode {

	MEETING_NOT_FOUND(NOT_FOUND, "모임을 찾을 수 없습니다."),
	EXCEED_MAX_MEETING_COUNT(BAD_REQUEST, "모임 최대 참여 및 개설 횟수를 초과했습니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
