package org.depromeet.sambad.moring.meeting.member.presentation.exception;

import static org.depromeet.sambad.moring.meeting.member.presentation.exception.MeetingMemberExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class MeetingMemberNotFoundException extends BusinessException {
	public MeetingMemberNotFoundException() {
		super(MEETING_MEMBER_NOT_FOUND);
	}
}
