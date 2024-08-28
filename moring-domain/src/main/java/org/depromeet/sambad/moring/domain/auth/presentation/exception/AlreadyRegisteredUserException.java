package org.depromeet.sambad.moring.domain.auth.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class AlreadyRegisteredUserException extends BusinessException {
	public AlreadyRegisteredUserException() {
		super(AuthExceptionCode.ALREADY_REGISTERED_USER);
	}
}
