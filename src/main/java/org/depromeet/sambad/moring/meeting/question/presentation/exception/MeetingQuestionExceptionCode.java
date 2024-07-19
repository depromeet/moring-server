package org.depromeet.sambad.moring.meeting.question.presentation.exception;

import static org.springframework.http.HttpStatus.*;

import org.depromeet.sambad.moring.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeetingQuestionExceptionCode implements ExceptionCode {

	INVALID_MEETING_MEMBER_TARGET(BAD_REQUEST, "본인 혹은 다른 모임의 모임원은 대상이 될 수 없습니다."),

	NOT_FOUND_MEETING_QUESTION(NOT_FOUND, "모임 릴레이 질문이 존재하지 않습니다."),

	DUPLICATE_MEETING_QUESTION(CONFLICT, "이미 등록한 릴레이 질문입니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
