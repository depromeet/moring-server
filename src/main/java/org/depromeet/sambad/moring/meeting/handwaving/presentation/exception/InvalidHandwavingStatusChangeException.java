package org.depromeet.sambad.moring.meeting.handwaving.presentation.exception;

import static org.depromeet.sambad.moring.meeting.handwaving.presentation.exception.HandwavingExceptionCode.INVALID_HAND_WAVING_STATUS_CHANGE;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class InvalidHandwavingStatusChangeException extends BusinessException {

	public InvalidHandwavingStatusChangeException() {
		super(INVALID_HAND_WAVING_STATUS_CHANGE);
	}
}
