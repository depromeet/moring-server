package org.depromeet.sambad.moring.event.presentation.response;

import org.depromeet.sambad.moring.event.domain.Event;
import org.depromeet.sambad.moring.event.domain.EventType;

public record PollingEventListResponseDetail(
	Long eventId,
	EventType eventType
) {
	public static PollingEventListResponseDetail from(Event event) {
		return new PollingEventListResponseDetail(
			event.getId(),
			event.getType()
		);
	}
}