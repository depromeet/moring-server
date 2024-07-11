package org.depromeet.sambad.moyeo.user.presentation.exception;

import static org.depromeet.sambad.moyeo.user.presentation.exception.UserExceptionCode.*;

import org.depromeet.sambad.moyeo.common.exception.BusinessException;

public class NotFoundUserException extends BusinessException {
	public NotFoundUserException() {
		super(NOT_FOUND_USER);
	}
}
