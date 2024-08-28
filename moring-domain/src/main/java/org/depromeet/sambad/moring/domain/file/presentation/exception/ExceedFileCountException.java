package org.depromeet.sambad.moring.domain.file.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class ExceedFileCountException extends BusinessException {
	public ExceedFileCountException() {
		super(FileExceptionCode.EXCEED_FILE_COUNT);
	}
}
