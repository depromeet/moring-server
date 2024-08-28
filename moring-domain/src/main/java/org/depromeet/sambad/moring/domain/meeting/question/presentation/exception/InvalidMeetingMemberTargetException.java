package org.depromeet.sambad.moring.domain.meeting.question.presentation.exception;

import static org.depromeet.sambad.moring.domain.meeting.question.presentation.exception.MeetingQuestionExceptionCode.*;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class InvalidMeetingMemberTargetException extends BusinessException {

	public InvalidMeetingMemberTargetException() {
		super(INVALID_MEETING_MEMBER_TARGET);
	}
}
