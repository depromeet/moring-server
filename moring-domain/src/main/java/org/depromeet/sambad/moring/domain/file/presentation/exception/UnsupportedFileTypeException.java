package org.depromeet.sambad.moring.domain.file.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class UnsupportedFileTypeException extends BusinessException {
	public UnsupportedFileTypeException() {
		super(FileExceptionCode.UNSUPPORTED_FILE_TYPE);
	}
}
