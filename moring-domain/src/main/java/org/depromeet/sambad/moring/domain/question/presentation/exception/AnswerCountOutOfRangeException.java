package org.depromeet.sambad.moring.domain.question.presentation.exception;

import static org.depromeet.sambad.moring.domain.question.presentation.exception.QuestionExceptionCode.*;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class AnswerCountOutOfRangeException extends BusinessException {
	public AnswerCountOutOfRangeException() {
		super(ANSWER_COUNT_OUT_OF_RANGE);
	}
}
