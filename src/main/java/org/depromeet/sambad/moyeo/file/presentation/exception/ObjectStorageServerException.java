package org.depromeet.sambad.moyeo.file.presentation.exception;

import static org.depromeet.sambad.moyeo.file.presentation.exception.FileExceptionCode.*;

import org.depromeet.sambad.moyeo.common.exception.BusinessException;

public class ObjectStorageServerException extends BusinessException {
	public ObjectStorageServerException() {
		super(OBJECTSTORAGE_SERVER_ERROR);
	}
}
