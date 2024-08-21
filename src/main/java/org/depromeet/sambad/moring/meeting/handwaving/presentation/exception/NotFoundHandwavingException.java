package org.depromeet.sambad.moring.meeting.handwaving.presentation.exception;

import static org.depromeet.sambad.moring.meeting.handwaving.presentation.exception.HandwavingExceptionCode.NOT_FOUND_HAND_WAVING;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class NotFoundHandwavingException extends BusinessException {
	public NotFoundHandwavingException() {
		super(NOT_FOUND_HAND_WAVING);
	}
}
