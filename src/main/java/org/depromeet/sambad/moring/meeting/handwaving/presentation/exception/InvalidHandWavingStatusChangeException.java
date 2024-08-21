package org.depromeet.sambad.moring.meeting.handwaving.presentation.exception;

import static org.depromeet.sambad.moring.meeting.handwaving.presentation.exception.HandWavingExceptionCode.INVALID_HAND_WAVING_STATUS_CHANGE;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class InvalidHandWavingStatusChangeException extends BusinessException {

	public InvalidHandWavingStatusChangeException() {
		super(INVALID_HAND_WAVING_STATUS_CHANGE);
	}
}
