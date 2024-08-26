package org.depromeet.sambad.moring.meeting.handwaving.domain;

import java.util.List;
import java.util.Objects;

import org.depromeet.sambad.moring.event.domain.Event;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record HandWavingSummary(
	Long handWavingId,
	HandWavingStatus status,
	@JsonIgnore
	Long eventId
) {
	public static HandWavingSummary from(HandWaving handWaving) {
		return new HandWavingSummary(handWaving.getId(), handWaving.getStatus(), handWaving.getEventId());
	}

	public static HandWavingSummary findSummaryByEvent(List<HandWavingSummary> handWavingSummaries, Event event) {
		return handWavingSummaries.stream()
			.filter(summary -> summary.shouldBelongTo(event))
			.findFirst()
			.orElse(null);
	}

	public boolean shouldBelongTo(Event event) {
		return Objects.equals(eventId, event.getId());
	}
}
