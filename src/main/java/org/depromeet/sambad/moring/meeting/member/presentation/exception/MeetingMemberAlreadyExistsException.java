package org.depromeet.sambad.moring.meeting.member.presentation.exception;

import static org.depromeet.sambad.moring.meeting.member.presentation.exception.MeetingMemberExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class MeetingMemberAlreadyExistsException extends BusinessException {
	public MeetingMemberAlreadyExistsException() {
		super(MEETING_MEMBER_ALREADY_EXISTS);
	}
}
