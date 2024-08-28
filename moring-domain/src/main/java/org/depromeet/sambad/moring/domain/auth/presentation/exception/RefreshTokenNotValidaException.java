package org.depromeet.sambad.moring.domain.auth.presentation.exception;

import static org.depromeet.sambad.moring.domain.auth.presentation.exception.AuthExceptionCode.*;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class RefreshTokenNotValidaException extends BusinessException {
	public RefreshTokenNotValidaException() {
		super(REFRESH_TOKEN_NOT_VALID);
	}
}
