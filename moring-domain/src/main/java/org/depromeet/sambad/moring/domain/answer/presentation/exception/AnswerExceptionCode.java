package org.depromeet.sambad.moring.domain.answer.presentation.exception;

import static org.springframework.http.HttpStatus.*;

import org.depromeet.sambad.moring.domain.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AnswerExceptionCode implements ExceptionCode {

	NOT_FOUND_ANSWER(NOT_FOUND, "답변이 존재하지 않습니다."),
	INVALID_ANSWER_CONTENT(BAD_REQUEST, "답변 내용이 NULL이거나 빈 문자열입니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}