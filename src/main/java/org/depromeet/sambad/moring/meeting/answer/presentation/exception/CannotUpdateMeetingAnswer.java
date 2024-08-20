package org.depromeet.sambad.moring.meeting.answer.presentation.exception;

import static org.depromeet.sambad.moring.meeting.answer.presentation.exception.MeetingAnswerExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class CannotUpdateMeetingAnswer extends BusinessException {
	public CannotUpdateMeetingAnswer() {
		super(CANNOT_UPDATE_MEETING_ANSWER);
	}
}
