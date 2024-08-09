package org.depromeet.sambad.moring.question.presentation.exception;

import static org.depromeet.sambad.moring.question.presentation.exception.QuestionExceptionCode.EXCEL_READ_ERROR;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class ExcelReadErrorException extends BusinessException {
	public ExcelReadErrorException() {
		super(EXCEL_READ_ERROR);
	}
}
