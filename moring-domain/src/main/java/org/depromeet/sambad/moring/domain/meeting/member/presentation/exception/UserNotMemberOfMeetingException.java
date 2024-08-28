package org.depromeet.sambad.moring.domain.meeting.member.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class UserNotMemberOfMeetingException extends BusinessException {
	public UserNotMemberOfMeetingException() {
		super(MeetingMemberExceptionCode.USER_NOT_MEMBER_OF_MEETING);
	}
}
