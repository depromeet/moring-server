package org.depromeet.sambad.moring.domain.meeting.handwaving.presentation.exception;

import static org.depromeet.sambad.moring.domain.meeting.handwaving.presentation.exception.HandWavingExceptionCode.*;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class NotFoundHandWavingException extends BusinessException {
	public NotFoundHandWavingException() {
		super(NOT_FOUND_HAND_WAVING);
	}
}
