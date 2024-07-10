package org.depromeet.sambad.moyeo.file.presentation.exception;

import static org.depromeet.sambad.moyeo.file.presentation.exception.FileExceptionCode.*;

import org.depromeet.sambad.moyeo.common.exception.BusinessException;

public class NotFoundFileException extends BusinessException {
	public NotFoundFileException() {
		super(NOT_FOUND_FILE);
	}
}
