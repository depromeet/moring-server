package org.depromeet.sambad.moring.question.presentation.exception;

import static org.depromeet.sambad.moring.question.presentation.exception.QuestionExceptionCode.ANSWER_COUNT_OUT_OF_RANGE;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class AnswerCountOutOfRangeException extends BusinessException {
	public AnswerCountOutOfRangeException() {
		super(ANSWER_COUNT_OUT_OF_RANGE);
	}
}
