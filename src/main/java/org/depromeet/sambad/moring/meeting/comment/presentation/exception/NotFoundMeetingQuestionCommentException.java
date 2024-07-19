package org.depromeet.sambad.moring.meeting.comment.presentation.exception;

import static org.depromeet.sambad.moring.meeting.comment.presentation.exception.MeetingQuestionCommentExceptionCode.NOT_FOUND_MEETING_QUESTION_COMMENT;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class NotFoundMeetingQuestionCommentException extends BusinessException {

	public NotFoundMeetingQuestionCommentException() {
		super(NOT_FOUND_MEETING_QUESTION_COMMENT);
	}
}
