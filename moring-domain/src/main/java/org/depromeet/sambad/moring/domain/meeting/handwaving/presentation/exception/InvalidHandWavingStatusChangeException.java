package org.depromeet.sambad.moring.domain.meeting.handwaving.presentation.exception;

import static org.depromeet.sambad.moring.domain.meeting.handwaving.presentation.exception.HandWavingExceptionCode.*;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class InvalidHandWavingStatusChangeException extends BusinessException {

	public InvalidHandWavingStatusChangeException() {
		super(INVALID_HAND_WAVING_STATUS_CHANGE);
	}
}
