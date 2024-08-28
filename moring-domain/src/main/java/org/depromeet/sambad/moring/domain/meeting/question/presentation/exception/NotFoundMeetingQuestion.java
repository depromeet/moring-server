package org.depromeet.sambad.moring.domain.meeting.question.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class NotFoundMeetingQuestion extends BusinessException {

	public NotFoundMeetingQuestion() {
		super(MeetingQuestionExceptionCode.NOT_FOUND_MEETING_QUESTION);
	}
}