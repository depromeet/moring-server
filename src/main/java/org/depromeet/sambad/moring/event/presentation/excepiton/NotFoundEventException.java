package org.depromeet.sambad.moring.event.presentation.excepiton;

import static org.depromeet.sambad.moring.event.presentation.excepiton.EventExceptionCode.NOT_FOUND_EVENT;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class NotFoundEventException extends BusinessException {
	public NotFoundEventException() {
		super(NOT_FOUND_EVENT);
	}
}
