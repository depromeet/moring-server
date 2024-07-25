package org.depromeet.sambad.moring.question.presentation.exception;

import static org.depromeet.sambad.moring.question.presentation.exception.QuestionExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class NotFoundAvailableQuestionException extends BusinessException {
	public NotFoundAvailableQuestionException() {
		super(NOT_FOUND_AVAILABLE_QUESTION);
	}
}
