package org.depromeet.sambad.moring.event.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.event.domain.Event;
import org.depromeet.sambad.moring.event.domain.EventStatus;
import org.depromeet.sambad.moring.event.domain.EventType;

import io.swagger.v3.oas.annotations.media.Schema;

public record EventResponse(
	@Schema(description = "이벤트 ID", example = "1", requiredMode = REQUIRED)
	Long eventId,

	@Schema(description = "이벤트 타입", example = "QUESTION_REGISTERED", requiredMode = REQUIRED)
	EventType type,

	@Schema(description = "이벤트 메시지", example = "질문이 등록되었습니다.", requiredMode = NOT_REQUIRED)
	List<String> messages,

	@Schema(description = "이벤트 상태", example = "ACTIVE", requiredMode = REQUIRED)
	EventStatus status,

	@Schema(description = "이벤트 타입 별로 필요한 추가 데이터", example = "{\"handWavingId\": 9}", requiredMode = NOT_REQUIRED)
	Object additionalData,

	@Schema(description = "이벤트 생성 시간 타임 스탬프", example = "1724252079282", requiredMode = REQUIRED)
	Long createdAt
) {
	public static EventResponse from(Event event, Object additionalData) {
		List<String> messages = Optional.ofNullable(event.getMessage())
			.map(message -> Arrays.asList(message.split("\n")))
			.orElse(List.of());

		return new EventResponse(
			event.getId(),
			event.getType(),
			messages,
			event.getStatus(),
			additionalData,
			event.getCreatedAtWithEpochMilli()
		);
	}
}
