package org.depromeet.sambad.moring.meeting.member.presentation.exception;

import static org.depromeet.sambad.moring.meeting.member.presentation.exception.MeetingMemberExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class ExceedMaxHostCountException extends BusinessException {
	public ExceedMaxHostCountException() {
		super(EXCEED_MAX_HOST_COUNT);
	}
}
