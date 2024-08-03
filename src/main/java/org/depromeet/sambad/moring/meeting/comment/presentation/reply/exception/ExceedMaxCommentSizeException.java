package org.depromeet.sambad.moring.meeting.comment.presentation.reply.exception;

import static org.depromeet.sambad.moring.meeting.comment.presentation.reply.exception.MeetingQuestionCommentReplyExceptionCode.EXCEED_MAX_COMMENT_SIZE;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class ExceedMaxCommentSizeException extends BusinessException {
	public ExceedMaxCommentSizeException() {
		super(EXCEED_MAX_COMMENT_SIZE);
	}
}
