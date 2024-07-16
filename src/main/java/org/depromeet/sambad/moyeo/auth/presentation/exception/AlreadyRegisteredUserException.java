package org.depromeet.sambad.moyeo.auth.presentation.exception;

import org.depromeet.sambad.moyeo.common.exception.BusinessException;

import static org.depromeet.sambad.moyeo.auth.presentation.exception.AuthExceptionCode.ALREADY_REGISTERED_USER;

public class AlreadyRegisteredUserException extends BusinessException {
	public AlreadyRegisteredUserException() {
		super(ALREADY_REGISTERED_USER);
	}
}
