package org.depromeet.sambad.moring.meeting.comment.presentation.comment.exception;

import static org.depromeet.sambad.moring.meeting.comment.presentation.comment.exception.MeetingQuestionCommentExceptionCode.INVALID_COMMENT_WRITER;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class InvalidCommentWriterException extends BusinessException {
	public InvalidCommentWriterException() {
		super(INVALID_COMMENT_WRITER);
	}
}
