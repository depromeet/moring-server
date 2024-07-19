package org.depromeet.sambad.moring.meeting.comment.presentation.reply.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.depromeet.sambad.moring.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeetingQuestionCommentReplyExceptionCode implements ExceptionCode {

	NOT_FOUND_MEETING_QUESTION_COMMENT_REPLY(NOT_FOUND, "릴레이 질문에 대한 코멘트 답글이 존재하지 않습니다."),
	INVALID_COMMENT_REPLY_WRITER(BAD_REQUEST, "작성자만 코멘트 답글을 삭제할 수 있습니다.")
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
