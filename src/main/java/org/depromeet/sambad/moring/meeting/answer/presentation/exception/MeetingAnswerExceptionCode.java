package org.depromeet.sambad.moring.meeting.answer.presentation.exception;

import static org.springframework.http.HttpStatus.*;

import org.depromeet.sambad.moring.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeetingAnswerExceptionCode implements ExceptionCode {

	CANNOT_UPDATE_MEETING_ANSWER(FORBIDDEN, "수정 권한이 없는 릴레이 질문 답변입니다."),

	DUPLICATE_MEETING_ANSWER(CONFLICT, "등록된 답변이 존재합니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
