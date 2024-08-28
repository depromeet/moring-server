package org.depromeet.sambad.moring.domain.meeting.member.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class MeetingMemberAlreadyExistsException extends BusinessException {
	public MeetingMemberAlreadyExistsException() {
		super(MeetingMemberExceptionCode.MEETING_MEMBER_ALREADY_EXISTS);
	}
}
