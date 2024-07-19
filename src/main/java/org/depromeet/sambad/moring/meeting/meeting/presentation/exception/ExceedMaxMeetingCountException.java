package org.depromeet.sambad.moring.meeting.meeting.presentation.exception;

import static org.depromeet.sambad.moring.meeting.meeting.presentation.exception.MeetingExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class ExceedMaxMeetingCountException extends BusinessException {
	public ExceedMaxMeetingCountException() {
		super(EXCEED_MAX_MEETING_COUNT);
	}
}
