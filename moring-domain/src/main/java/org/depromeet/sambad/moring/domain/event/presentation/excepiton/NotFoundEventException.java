package org.depromeet.sambad.moring.domain.event.presentation.excepiton;

import static org.depromeet.sambad.moring.domain.event.presentation.excepiton.EventExceptionCode.*;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class NotFoundEventException extends BusinessException {
	public NotFoundEventException() {
		super(NOT_FOUND_EVENT);
	}
}
