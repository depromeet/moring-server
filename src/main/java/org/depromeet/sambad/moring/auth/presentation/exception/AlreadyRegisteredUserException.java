package org.depromeet.sambad.moring.auth.presentation.exception;

import static org.depromeet.sambad.moring.auth.presentation.exception.AuthExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class AlreadyRegisteredUserException extends BusinessException {
	public AlreadyRegisteredUserException() {
		super(ALREADY_REGISTERED_USER);
	}
}
