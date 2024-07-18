package org.depromeet.sambad.moring.file.presentation.exception;

import static org.depromeet.sambad.moring.file.presentation.exception.FileExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class ObjectStorageServerException extends BusinessException {
	public ObjectStorageServerException() {
		super(OBJECTSTORAGE_SERVER_ERROR);
	}
}
