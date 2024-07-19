package org.depromeet.sambad.moring.meeting.question.presentation.exception;

import static org.depromeet.sambad.moring.meeting.question.presentation.exception.MeetingQuestionExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class NotFoundMeetingQuestion extends BusinessException {

	public NotFoundMeetingQuestion() {
		super(NOT_FOUND_MEETING_QUESTION);
	}
}