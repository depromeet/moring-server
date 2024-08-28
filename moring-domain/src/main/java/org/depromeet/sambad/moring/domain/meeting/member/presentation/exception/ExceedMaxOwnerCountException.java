package org.depromeet.sambad.moring.domain.meeting.member.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class ExceedMaxOwnerCountException extends BusinessException {
	public ExceedMaxOwnerCountException() {
		super(MeetingMemberExceptionCode.EXCEED_MAX_OWNER_COUNT);
	}
}
