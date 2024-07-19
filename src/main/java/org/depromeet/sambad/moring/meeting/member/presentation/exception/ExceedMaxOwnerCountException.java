package org.depromeet.sambad.moring.meeting.member.presentation.exception;

import static org.depromeet.sambad.moring.meeting.member.presentation.exception.MeetingMemberExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class ExceedMaxOwnerCountException extends BusinessException {
	public ExceedMaxOwnerCountException() {
		super(EXCEED_MAX_OWNER_COUNT);
	}
}
