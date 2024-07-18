package org.depromeet.sambad.moring.file.presentation.exception;

import static org.depromeet.sambad.moring.file.presentation.exception.FileExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class UnsupportedFileTypeException extends BusinessException {
	public UnsupportedFileTypeException() {
		super(UNSUPPORTED_FILE_TYPE);
	}
}
