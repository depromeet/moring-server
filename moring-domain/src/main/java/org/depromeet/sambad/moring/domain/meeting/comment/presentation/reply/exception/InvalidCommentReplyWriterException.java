package org.depromeet.sambad.moring.domain.meeting.comment.presentation.reply.exception;

import org.depromeet.sambad.moring.domain.common.exception.BusinessException;

public class InvalidCommentReplyWriterException extends BusinessException {
	public InvalidCommentReplyWriterException() {
		super(MeetingQuestionCommentReplyExceptionCode.INVALID_COMMENT_REPLY_WRITER);
	}
}
