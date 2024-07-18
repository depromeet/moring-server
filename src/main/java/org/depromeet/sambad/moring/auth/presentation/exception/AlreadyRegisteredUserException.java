package org.depromeet.sambad.moring.auth.presentation.exception;

import org.depromeet.sambad.moring.common.exception.BusinessException;

import static org.depromeet.sambad.moring.auth.presentation.exception.AuthExceptionCode.ALREADY_REGISTERED_USER;

public class AlreadyRegisteredUserException extends BusinessException {
	public AlreadyRegisteredUserException() {
		super(ALREADY_REGISTERED_USER);
	}
}
