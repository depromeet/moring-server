package org.depromeet.sambad.moring.meeting.meeting.presentation.exception;

import static org.depromeet.sambad.moring.meeting.meeting.presentation.exception.MeetingExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class MeetingNotFoundException extends BusinessException {
	public MeetingNotFoundException() {
		super(MEETING_NOT_FOUND);
	}
}
