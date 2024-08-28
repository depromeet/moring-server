package org.depromeet.sambad.moring.domain.question.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class NotFoundAvailableQuestionException extends BusinessException {
	public NotFoundAvailableQuestionException() {
		super(QuestionExceptionCode.NOT_FOUND_AVAILABLE_QUESTION);
	}
}
