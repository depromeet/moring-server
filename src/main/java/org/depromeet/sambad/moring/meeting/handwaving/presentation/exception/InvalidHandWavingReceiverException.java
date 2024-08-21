package org.depromeet.sambad.moring.meeting.handwaving.presentation.exception;

import static org.depromeet.sambad.moring.meeting.handwaving.presentation.exception.HandWavingExceptionCode.INVALID_HAND_WAVING_RECEIVER;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class InvalidHandWavingReceiverException extends BusinessException {
	public InvalidHandWavingReceiverException() {
		super(INVALID_HAND_WAVING_RECEIVER);
	}
}
