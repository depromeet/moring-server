package org.depromeet.sambad.moring.meeting.answer.presentation.exception;

import static org.depromeet.sambad.moring.meeting.answer.presentation.exception.MeetingAnswerExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class DuplicateMeetingAnswerException extends BusinessException {

	public DuplicateMeetingAnswerException() {
		super(DUPLICATE_MEETING_ANSWER);
	}
}