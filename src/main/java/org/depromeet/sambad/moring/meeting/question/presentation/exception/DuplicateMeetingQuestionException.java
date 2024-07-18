package org.depromeet.sambad.moring.meeting.question.presentation.exception;

import static org.depromeet.sambad.moring.meeting.question.presentation.exception.MeetingQuestionExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class DuplicateMeetingQuestionException extends BusinessException {

	public DuplicateMeetingQuestionException() {
		super(DUPLICATE_MEETING_QUESTION);
	}
}
