package org.depromeet.sambad.moring.file.presentation.exception;

import static org.depromeet.sambad.moring.file.presentation.exception.FileExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class NotFoundFileException extends BusinessException {
	public NotFoundFileException() {
		super(NOT_FOUND_FILE);
	}
}
