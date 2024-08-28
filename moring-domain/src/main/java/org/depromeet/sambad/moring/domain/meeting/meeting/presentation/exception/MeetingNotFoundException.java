package org.depromeet.sambad.moring.domain.meeting.meeting.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class MeetingNotFoundException extends BusinessException {
	public MeetingNotFoundException() {
		super(MeetingExceptionCode.MEETING_NOT_FOUND);
	}
}
