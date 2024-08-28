package org.depromeet.sambad.moring.domain.meeting.question.presentation.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class DuplicateMeetingQuestionException extends BusinessException {

	public DuplicateMeetingQuestionException() {
		super(MeetingQuestionExceptionCode.DUPLICATE_MEETING_QUESTION);
	}
}
