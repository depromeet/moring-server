package org.depromeet.sambad.moring.auth.presentation.exception;

import static org.depromeet.sambad.moring.auth.presentation.exception.AuthExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class RefreshTokenNotValidaException extends BusinessException {
	public RefreshTokenNotValidaException() {
		super(REFRESH_TOKEN_NOT_VALID);
	}
}
