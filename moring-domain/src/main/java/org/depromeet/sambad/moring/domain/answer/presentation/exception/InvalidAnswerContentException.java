package org.depromeet.sambad.moring.domain.answer.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class InvalidAnswerContentException extends BusinessException {
	public InvalidAnswerContentException() {
		super(AnswerExceptionCode.INVALID_ANSWER_CONTENT);
	}
}
