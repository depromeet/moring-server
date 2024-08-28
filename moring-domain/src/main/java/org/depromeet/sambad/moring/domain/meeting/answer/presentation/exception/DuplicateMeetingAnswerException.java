package org.depromeet.sambad.moring.domain.meeting.answer.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class DuplicateMeetingAnswerException extends BusinessException {

	public DuplicateMeetingAnswerException() {
		super(MeetingAnswerExceptionCode.DUPLICATE_MEETING_ANSWER);
	}
}