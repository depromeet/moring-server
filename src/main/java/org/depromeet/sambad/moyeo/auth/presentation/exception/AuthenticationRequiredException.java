package org.depromeet.sambad.moyeo.auth.presentation.exception;

import org.depromeet.sambad.moyeo.common.exception.BusinessException;

import static org.depromeet.sambad.moyeo.auth.presentation.exception.AuthExceptionCode.AUTHENTICATION_REQUIRED;

public class AuthenticationRequiredException extends BusinessException {
	public AuthenticationRequiredException() {
		super(AUTHENTICATION_REQUIRED);
	}
}
