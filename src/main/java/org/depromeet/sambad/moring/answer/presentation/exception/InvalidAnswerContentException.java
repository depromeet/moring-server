package org.depromeet.sambad.moring.answer.presentation.exception;

import static org.depromeet.sambad.moring.answer.presentation.exception.AnswerExceptionCode.INVALID_ANSWER_CONTENT;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class InvalidAnswerContentException extends BusinessException {
	public InvalidAnswerContentException() {
		super(INVALID_ANSWER_CONTENT);
	}
}
