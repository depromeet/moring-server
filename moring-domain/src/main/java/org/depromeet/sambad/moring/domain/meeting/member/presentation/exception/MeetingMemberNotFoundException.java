package org.depromeet.sambad.moring.domain.meeting.member.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class MeetingMemberNotFoundException extends BusinessException {
	public MeetingMemberNotFoundException() {
		super(MeetingMemberExceptionCode.MEETING_MEMBER_NOT_FOUND);
	}
}
