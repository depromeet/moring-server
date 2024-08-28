package org.depromeet.sambad.moring.domain.file.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class NotFoundFileException extends BusinessException {
	public NotFoundFileException() {
		super(FileExceptionCode.NOT_FOUND_FILE);
	}
}
