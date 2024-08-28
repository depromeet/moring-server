package org.depromeet.sambad.moring.domain.meeting.question.presentation.exception;

import static org.depromeet.sambad.moring.domain.meeting.question.presentation.exception.MeetingQuestionExceptionCode.*;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class InvalidMeetingMemberNextTargetException extends BusinessException {

	public InvalidMeetingMemberNextTargetException() {
		super(INVALID_MEETING_MEMBER_NEXT_TARGET);
	}
}
