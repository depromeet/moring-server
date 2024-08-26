package org.depromeet.sambad.moring.event.presentation;

import java.util.List;

import org.depromeet.sambad.moring.event.application.EventService;
import org.depromeet.sambad.moring.event.domain.Event;
import org.depromeet.sambad.moring.event.presentation.response.EventListResponse;
import org.depromeet.sambad.moring.event.presentation.response.EventResponse;
import org.depromeet.sambad.moring.meeting.handwaving.application.HandWavingService;
import org.depromeet.sambad.moring.meeting.handwaving.domain.HandWavingSummary;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EventFacade {

	private final EventService eventService;
	private final HandWavingService handWavingService;

	public EventListResponse getEventsResponse(Long userId, Long meetingId) {
		List<Event> events = eventService.getEvents(userId, meetingId);
		List<HandWavingSummary> handWavingSummaries = fetchHandWavingEventSummaries(events);

		return mapToEventListResponse(events, handWavingSummaries);
	}

	private List<HandWavingSummary> fetchHandWavingEventSummaries(List<Event> events) {
		List<Event> handWavingEvents = events.stream()
			.filter(Event::isHandWavingEvent)
			.toList();

		return handWavingService.getHandWavingSummariesBy(handWavingEvents);
	}

	private EventListResponse mapToEventListResponse(List<Event> events, List<HandWavingSummary> handWavingSummaries) {
		List<EventResponse> responses = events.stream()
			.map(event -> mapToResponseConsiderEventType(event, handWavingSummaries))
			.toList();

		return EventListResponse.from(responses);
	}

	private EventResponse mapToResponseConsiderEventType(Event event, List<HandWavingSummary> handWavingSummaries) {
		HandWavingSummary additionalData = HandWavingSummary.findSummaryByEvent(handWavingSummaries, event);

		return EventResponse.from(event, additionalData);
	}
}
