package org.depromeet.sambad.moring.domain.meeting.handwaving.presentation.exception;

import static org.depromeet.sambad.moring.domain.meeting.handwaving.presentation.exception.HandWavingExceptionCode.*;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class InvalidHandWavingReceiverException extends BusinessException {
	public InvalidHandWavingReceiverException() {
		super(INVALID_HAND_WAVING_RECEIVER);
	}
}
