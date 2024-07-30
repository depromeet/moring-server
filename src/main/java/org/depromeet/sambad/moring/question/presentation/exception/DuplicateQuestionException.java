package org.depromeet.sambad.moring.question.presentation.exception;

import static org.depromeet.sambad.moring.question.presentation.exception.QuestionExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class DuplicateQuestionException extends BusinessException {

	public DuplicateQuestionException() {
		super(DUPLICATE_QUESTION);
	}
}
