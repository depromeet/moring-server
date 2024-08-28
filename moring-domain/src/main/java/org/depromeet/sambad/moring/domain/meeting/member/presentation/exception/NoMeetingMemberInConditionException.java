package org.depromeet.sambad.moring.domain.meeting.member.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class NoMeetingMemberInConditionException extends BusinessException {
	public NoMeetingMemberInConditionException() {
		super(MeetingMemberExceptionCode.NO_MEETING_MEMBER_IN_CONDITION);
	}
}
