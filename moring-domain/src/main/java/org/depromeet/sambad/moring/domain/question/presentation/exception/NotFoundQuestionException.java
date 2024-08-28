package org.depromeet.sambad.moring.domain.question.presentation.exception;

import static org.depromeet.sambad.moring.domain.question.presentation.exception.QuestionExceptionCode.*;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class NotFoundQuestionException extends BusinessException {

	public NotFoundQuestionException() {
		super(NOT_FOUND_QUESTION);
	}
}