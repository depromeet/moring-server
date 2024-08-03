package org.depromeet.sambad.moring.event.presentation;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.Set;

import org.depromeet.sambad.moring.event.domain.EventType;

import io.swagger.v3.oas.annotations.media.Schema;

public record PollingEventResponse(
	@Schema(description = "새로운 턴의 릴레이 질문에서, 질문인이 질문을 등록했는지 여부와 질문인이 로그인한 유저인지 여부인지 응답합니다.",
		examples = {"QUESTION_REGISTERED", "TARGET_MEMBER"}, requiredMode = REQUIRED)
	Set<EventType> events
) {

}