package org.depromeet.sambad.moring.meeting.member.presentation.exception;

import static org.depromeet.sambad.moring.meeting.member.presentation.exception.MeetingMemberExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class UserNotMemberOfMeetingException extends BusinessException {
	public UserNotMemberOfMeetingException() {
		super(USER_NOT_MEMBER_OF_MEETING);
	}
}
