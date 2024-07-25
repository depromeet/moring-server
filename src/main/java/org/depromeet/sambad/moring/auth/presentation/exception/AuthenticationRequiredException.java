package org.depromeet.sambad.moring.auth.presentation.exception;

import static org.depromeet.sambad.moring.auth.presentation.exception.AuthExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class AuthenticationRequiredException extends BusinessException {
	public AuthenticationRequiredException() {
		super(AUTHENTICATION_REQUIRED);
	}
}
