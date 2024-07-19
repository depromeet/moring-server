package org.depromeet.sambad.moring.answer.presentation.exception;

import static org.depromeet.sambad.moring.answer.presentation.exception.AnswerExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class NotFoundAnswerException extends BusinessException {

	public NotFoundAnswerException() {
		super(NOT_FOUND_ANSWER);
	}
}