package org.depromeet.sambad.moring.domain.answer.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class NotFoundAnswerException extends BusinessException {

	public NotFoundAnswerException() {
		super(AnswerExceptionCode.NOT_FOUND_ANSWER);
	}
}