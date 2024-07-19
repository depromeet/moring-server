package org.depromeet.sambad.moring.meeting.comment.presentation.reply.exception;

import static org.depromeet.sambad.moring.meeting.comment.presentation.reply.exception.MeetingQuestionCommentReplyExceptionCode.INVALID_COMMENT_REPLY_WRITER;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class InvalidCommentReplyWriterException extends BusinessException {
	public InvalidCommentReplyWriterException() {
		super(INVALID_COMMENT_REPLY_WRITER);
	}
}
