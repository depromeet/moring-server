package org.depromeet.sambad.moring.meeting.handWaving.presentation.exception;

import static org.depromeet.sambad.moring.meeting.handWaving.presentation.exception.HandWavingExceptionCode.INVALID_STATUS_CHANGE;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class InvalidStatusChangeException extends BusinessException {

	public InvalidStatusChangeException() {
		super(INVALID_STATUS_CHANGE);
	}
}
