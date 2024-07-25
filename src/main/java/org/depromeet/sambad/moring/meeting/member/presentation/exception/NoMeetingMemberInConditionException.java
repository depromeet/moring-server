package org.depromeet.sambad.moring.meeting.member.presentation.exception;

import static org.depromeet.sambad.moring.meeting.member.presentation.exception.MeetingMemberExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class NoMeetingMemberInConditionException extends BusinessException {
	public NoMeetingMemberInConditionException() {
		super(NO_MEETING_MEMBER_IN_CONDITION);
	}
}
