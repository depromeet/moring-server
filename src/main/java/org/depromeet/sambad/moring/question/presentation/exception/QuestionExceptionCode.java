package org.depromeet.sambad.moring.question.presentation.exception;

import static org.springframework.http.HttpStatus.*;

import org.depromeet.sambad.moring.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestionExceptionCode implements ExceptionCode {

	ANSWER_COUNT_OUT_OF_RANGE(BAD_REQUEST, "답변 개수가 범위를 벗어났습니다. 2개 이상 16개 이하로 입력해주세요."),

	NOT_FOUND_QUESTION(NOT_FOUND, "릴레이 질문이 존재하지 않습니다."),
	NOT_FOUND_AVAILABLE_QUESTION(NOT_FOUND, "사용 가능한 질문이 존재하지 않습니다."),

	DUPLICATE_QUESTION(CONFLICT, "이미 등록한 질문입니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
