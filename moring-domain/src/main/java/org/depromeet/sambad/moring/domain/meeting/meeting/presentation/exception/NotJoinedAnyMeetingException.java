package org.depromeet.sambad.moring.domain.meeting.meeting.presentation.exception;

import static org.depromeet.sambad.moring.domain.meeting.meeting.presentation.exception.MeetingExceptionCode.*;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class NotJoinedAnyMeetingException extends BusinessException {
	public NotJoinedAnyMeetingException() {
		super(NOT_JOINED_ANY_MEETING);
	}
}
