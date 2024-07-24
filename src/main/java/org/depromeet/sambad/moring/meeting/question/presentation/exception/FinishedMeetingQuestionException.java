package org.depromeet.sambad.moring.meeting.question.presentation.exception;

import static org.depromeet.sambad.moring.meeting.question.presentation.exception.MeetingQuestionExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class FinishedMeetingQuestionException extends BusinessException {

	public FinishedMeetingQuestionException() {
		super(FINISHED_MEETING_QUESTION);
	}
}
