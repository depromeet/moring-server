package org.depromeet.sambad.moring.user.presentation.exception;

import static org.depromeet.sambad.moring.user.presentation.exception.UserExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class NotFoundUserException extends BusinessException {
	public NotFoundUserException() {
		super(NOT_FOUND_USER);
	}
}
