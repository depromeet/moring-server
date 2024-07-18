package org.depromeet.sambad.moring.meetingQuestion.presentation.exception;

import static org.springframework.http.HttpStatus.*;

import org.depromeet.sambad.moring.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeetingQuestionExceptionCode implements ExceptionCode {

	DUPLICATE_MEETING_QUESTION(CONFLICT, "등록된 릴레이 질문입니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
