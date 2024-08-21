package org.depromeet.sambad.moring.event.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.event.domain.Event;

import io.swagger.v3.oas.annotations.media.Schema;

public record EventListResponse(
	@Schema(description = "이벤트 목록", requiredMode = REQUIRED)
	List<EventResponse> contents
) {

	public static EventListResponse from(List<Event> events) {
		List<EventResponse> contents = events.stream()
			.map(EventResponse::from)
			.toList();

		return new EventListResponse(contents);
	}
}
