package org.depromeet.sambad.moring.file.presentation.exception;

import static org.depromeet.sambad.moring.file.presentation.exception.FileExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class ExceedFileCountException extends BusinessException {
	public ExceedFileCountException() {
		super(EXCEED_FILE_COUNT);
	}
}
