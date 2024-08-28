package org.depromeet.sambad.moring.domain.file.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class ObjectStorageServerException extends BusinessException {
	public ObjectStorageServerException() {
		super(FileExceptionCode.OBJECTSTORAGE_SERVER_ERROR);
	}
}
