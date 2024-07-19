package org.depromeet.sambad.moring.meeting.comment.presentation.reply.exception;

import static org.depromeet.sambad.moring.meeting.comment.presentation.reply.exception.MeetingQuestionCommentReplyExceptionCode.NOT_FOUND_MEETING_QUESTION_COMMENT_REPLY;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class NotFoundMeetingQuestionCommentReplyException extends BusinessException {
	public NotFoundMeetingQuestionCommentReplyException() {
		super(NOT_FOUND_MEETING_QUESTION_COMMENT_REPLY);
	}
}
