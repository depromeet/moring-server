package org.depromeet.sambad.moring.domain.event.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record EventListResponse(
	@Schema(description = "이벤트 목록", requiredMode = REQUIRED)
	List<EventResponse> contents
) {

	public static EventListResponse from(List<EventResponse> events) {
		return new EventListResponse(events);
	}
}
