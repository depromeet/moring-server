package org.depromeet.sambad.moring.meeting.handWaving.presentation.exception;

import static org.depromeet.sambad.moring.meeting.handWaving.presentation.exception.HandWavingExceptionCode.NOT_FOUND_HAND_WAVING;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class NotFoundHandWavingException extends BusinessException {
	public NotFoundHandWavingException() {
		super(NOT_FOUND_HAND_WAVING);
	}
}
