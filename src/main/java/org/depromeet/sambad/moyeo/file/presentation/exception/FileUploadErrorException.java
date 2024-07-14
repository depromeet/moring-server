package org.depromeet.sambad.moyeo.file.presentation.exception;

import static org.depromeet.sambad.moyeo.file.presentation.exception.FileExceptionCode.FILE_UPLOAD_ERROR;

import org.depromeet.sambad.moyeo.common.exception.BusinessException;

public class FileUploadErrorException extends BusinessException {
	public FileUploadErrorException () {
		super(FILE_UPLOAD_ERROR);
	}
}
