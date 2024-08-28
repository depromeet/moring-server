package org.depromeet.sambad.moring.domain.event.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import org.depromeet.sambad.moring.domain.event.domain.Event;
import org.depromeet.sambad.moring.domain.event.domain.EventType;

import io.swagger.v3.oas.annotations.media.Schema;

public record PollingEventListResponseDetail(
	@Schema(description = "이벤트 ID", example = "1", requiredMode = REQUIRED)
	Long eventId,

	@Schema(description = "이벤트 타입", example = "QUESTION_REGISTERED", requiredMode = REQUIRED)
	EventType eventType
) {
	public static PollingEventListResponseDetail from(Event event) {
		return new PollingEventListResponseDetail(
			event.getId(),
			event.getType()
		);
	}
}