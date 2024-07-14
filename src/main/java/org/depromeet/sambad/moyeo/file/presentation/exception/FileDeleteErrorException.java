package org.depromeet.sambad.moyeo.file.presentation.exception;

import static org.depromeet.sambad.moyeo.file.presentation.exception.FileExceptionCode.FILE_DELETE_ERROR;

import org.depromeet.sambad.moyeo.common.exception.BusinessException;

public class FileDeleteErrorException extends BusinessException {
	public FileDeleteErrorException() {
		super(FILE_DELETE_ERROR);
	}
}
