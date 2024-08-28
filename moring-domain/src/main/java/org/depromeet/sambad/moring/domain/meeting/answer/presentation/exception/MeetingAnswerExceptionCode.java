package org.depromeet.sambad.moring.domain.meeting.answer.presentation.exception;

import static org.springframework.http.HttpStatus.*;

import org.depromeet.sambad.moring.domain.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeetingAnswerExceptionCode implements ExceptionCode {

	DUPLICATE_MEETING_ANSWER(CONFLICT, "등록된 답변이 존재합니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
