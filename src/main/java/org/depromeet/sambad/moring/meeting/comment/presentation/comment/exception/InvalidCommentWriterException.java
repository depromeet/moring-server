package org.depromeet.sambad.moring.meeting.comment.presentation.comment.exception;

import static org.depromeet.sambad.moring.meeting.comment.presentation.comment.exception.MeetingQuestionCommentExceptionCode.*;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class InvalidCommentWriterException extends BusinessException {
	public InvalidCommentWriterException() {
		super(INVALID_COMMENT_WRITER);
	}
}
