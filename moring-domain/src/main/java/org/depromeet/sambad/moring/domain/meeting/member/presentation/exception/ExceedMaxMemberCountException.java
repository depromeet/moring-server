package org.depromeet.sambad.moring.domain.meeting.member.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class ExceedMaxMemberCountException extends BusinessException {
	public ExceedMaxMemberCountException() {
		super(MeetingMemberExceptionCode.EXCEED_MAX_MEMBER_COUNT);
	}
}
