package org.depromeet.sambad.moring.auth.presentation.exception;

import org.depromeet.sambad.moring.common.exception.BusinessException;

import static org.depromeet.sambad.moring.auth.presentation.exception.AuthExceptionCode.AUTHENTICATION_REQUIRED;

public class AuthenticationRequiredException extends BusinessException {
	public AuthenticationRequiredException() {
		super(AUTHENTICATION_REQUIRED);
	}
}
