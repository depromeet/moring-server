package org.depromeet.sambad.moring.domain.user.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class NotFoundUserException extends BusinessException {
	public NotFoundUserException() {
		super(UserExceptionCode.NOT_FOUND_USER);
	}
}
