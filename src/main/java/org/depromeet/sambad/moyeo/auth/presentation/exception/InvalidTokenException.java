package org.depromeet.sambad.moyeo.auth.presentation.exception;

import static org.depromeet.sambad.moyeo.auth.presentation.exception.AuthExceptionCode.INVALID_TOKEN;

public class InvalidTokenException extends BusinessAuthException {
	public InvalidTokenException() {
		super(INVALID_TOKEN);
	}
}
