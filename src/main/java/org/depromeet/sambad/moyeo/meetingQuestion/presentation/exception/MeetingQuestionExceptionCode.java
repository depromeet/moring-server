package org.depromeet.sambad.moyeo.meetingQuestion.presentation.exception;

import org.depromeet.sambad.moyeo.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeetingQuestionExceptionCode implements ExceptionCode {
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
