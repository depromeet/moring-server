package org.depromeet.sambad.moyeo.file.presentation.exception;

import static org.depromeet.sambad.moyeo.file.presentation.exception.FileExceptionCode.*;

import org.depromeet.sambad.moyeo.common.exception.BusinessException;

public class ExceedFileCountException extends BusinessException {
	public ExceedFileCountException() {
		super(EXCEED_FILE_COUNT);
	}
}
