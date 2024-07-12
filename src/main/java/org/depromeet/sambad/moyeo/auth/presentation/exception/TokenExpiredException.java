package org.depromeet.sambad.moyeo.auth.presentation.exception;

import static org.depromeet.sambad.moyeo.auth.presentation.exception.AuthExceptionCode.TOKEN_EXPIRED;

public class TokenExpiredException extends BusinessAuthException {
	public TokenExpiredException() {
		super(TOKEN_EXPIRED);
	}
}
