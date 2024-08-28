package org.depromeet.sambad.moring.domain.meeting.comment.presentation.reply.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class NotFoundMeetingQuestionCommentReplyException extends BusinessException {
	public NotFoundMeetingQuestionCommentReplyException() {
		super(MeetingQuestionCommentReplyExceptionCode.NOT_FOUND_MEETING_QUESTION_COMMENT_REPLY);
	}
}
