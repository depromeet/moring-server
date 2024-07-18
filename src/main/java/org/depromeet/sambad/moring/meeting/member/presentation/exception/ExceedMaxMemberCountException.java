package org.depromeet.sambad.moring.meeting.member.presentation.exception;

import static org.depromeet.sambad.moring.meeting.member.presentation.exception.MeetingMemberExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class ExceedMaxMemberCountException extends BusinessException {
	public ExceedMaxMemberCountException() {
		super(EXCEED_MAX_MEMBER_COUNT);
	}
}
