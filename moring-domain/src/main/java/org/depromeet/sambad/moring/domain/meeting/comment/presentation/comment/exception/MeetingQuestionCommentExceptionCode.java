package org.depromeet.sambad.moring.domain.meeting.comment.presentation.comment.exception;

import static org.springframework.http.HttpStatus.*;

import org.depromeet.sambad.moring.domain.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeetingQuestionCommentExceptionCode implements ExceptionCode {

	INVALID_COMMENT_WRITER(BAD_REQUEST, "작성자만 코멘트를 삭제할 수 있습니다."),

	NOT_FOUND_MEETING_QUESTION_COMMENT(NOT_FOUND, "릴레이 질문에 대한 코멘트가 존재하지 않습니다.");

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
