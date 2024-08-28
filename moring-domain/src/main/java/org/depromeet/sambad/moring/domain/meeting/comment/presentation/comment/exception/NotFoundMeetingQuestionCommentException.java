package org.depromeet.sambad.moring.domain.meeting.comment.presentation.comment.exception;

import static org.depromeet.sambad.moring.domain.meeting.comment.presentation.comment.exception.MeetingQuestionCommentExceptionCode.*;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class NotFoundMeetingQuestionCommentException extends BusinessException {

	public NotFoundMeetingQuestionCommentException() {
		super(NOT_FOUND_MEETING_QUESTION_COMMENT);
	}
}
