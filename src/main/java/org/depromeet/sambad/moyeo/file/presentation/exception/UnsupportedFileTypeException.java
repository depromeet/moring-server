package org.depromeet.sambad.moyeo.file.presentation.exception;

import static org.depromeet.sambad.moyeo.file.presentation.exception.FileExceptionCode.*;

import org.depromeet.sambad.moyeo.common.exception.BusinessException;

public class UnsupportedFileTypeException extends BusinessException {
	public UnsupportedFileTypeException() {
		super(UNSUPPORTED_FILE_TYPE);
	}
}
