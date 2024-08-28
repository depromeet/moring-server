package org.depromeet.sambad.moring.domain.meeting.meeting.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class ExceedMaxMeetingCountException extends BusinessException {
	public ExceedMaxMeetingCountException() {
		super(MeetingExceptionCode.EXCEED_MAX_MEETING_COUNT);
	}
}
