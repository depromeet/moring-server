package org.depromeet.sambad.moring.domain.auth.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class AuthenticationRequiredException extends BusinessException {
	public AuthenticationRequiredException() {
		super(AuthExceptionCode.AUTHENTICATION_REQUIRED);
	}
}
