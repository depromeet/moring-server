package org.depromeet.sambad.moring.meeting.question.presentation.exception;

import static org.depromeet.sambad.moring.meeting.question.presentation.exception.MeetingQuestionExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class InvalidMeetingMemberTargetException extends BusinessException {

	public InvalidMeetingMemberTargetException() {
		super(INVALID_MEETING_MEMBER_TARGET);
	}
}
