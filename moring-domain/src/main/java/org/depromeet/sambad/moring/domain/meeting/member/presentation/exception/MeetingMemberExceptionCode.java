package org.depromeet.sambad.moring.domain.meeting.member.presentation.exception;

import static org.springframework.http.HttpStatus.*;

import org.depromeet.sambad.moring.domain.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeetingMemberExceptionCode implements ExceptionCode {
	EXCEED_MAX_MEMBER_COUNT(BAD_REQUEST, "가입하려는 모임이 정원을 초과했습니다."),
	EXCEED_MAX_OWNER_COUNT(BAD_REQUEST, "최대 모임장 수를 초과했습니다."),

	USER_NOT_MEMBER_OF_MEETING(FORBIDDEN, "사용자가 해당 모임에 가입되어 있지 않습니다."),

	MEETING_MEMBER_NOT_FOUND(NOT_FOUND, "모임 참여자를 찾을 수 없습니다."),
	NO_MEETING_MEMBER_IN_CONDITION(NOT_FOUND, "조건에 맞는 모임 참여자가 없습니다."),

	MEETING_MEMBER_ALREADY_EXISTS(CONFLICT, "이미 모임에 가입되어 있습니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
