package org.depromeet.sambad.moring.event.application;

import static org.depromeet.sambad.moring.event.domain.EventStatus.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.depromeet.sambad.moring.event.domain.Event;
import org.depromeet.sambad.moring.event.domain.EventType;
import org.depromeet.sambad.moring.event.infrastructure.EventProperties;
import org.depromeet.sambad.moring.event.presentation.excepiton.NotFoundEventException;
import org.depromeet.sambad.moring.event.presentation.response.PollingEventListResponse;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

	private final EventRepository eventRepository;
	private final EventMessageTemplateRepository eventMessageTemplateRepository;
	private final MeetingMemberValidator meetingMemberValidator;

	private final EventProperties eventProperties;

	public void publish(Long userId, Long meetingId, EventType type) {
		this.publish(userId, meetingId, type, Map.of(), Map.of());
	}

	public void publish(
		Long userId, Long meetingId, EventType type, Map<String, String> contentsMap, Map<String, Object> additionalData
	) {
		if (meetingMemberValidator.isNotUserOfMeeting(userId, meetingId)) {
			log.warn("User is not member of meeting. userId: {}, meetingId: {}", userId, meetingId);
			return;
		}

		String message = constructEventMessage(type, contentsMap);

		Event event = Event.publish(userId, meetingId, type, message, additionalData);
		eventRepository.save(event);
	}

	public void inactivate(Long eventId) {
		Event event = getEventById(eventId);
		event.inactivate();
		eventRepository.save(event);
	}

	@Transactional(readOnly = true)
	public List<Event> getEvents(Long userId, Long meetingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		LocalDateTime keepDays = LocalDateTime.now().minusDays(eventProperties.keepDays());

		return eventRepository.findByUserIdAndMeetingIdAndCreatedAtAfterOrderByCreatedAtDesc(
			userId, meetingId, keepDays);
	}

	public void inactivateLastEventByType(Long userId, Long meetingId, EventType type) {
		eventRepository.findFirstByUserIdAndMeetingIdAndStatusAndType(userId, meetingId, ACTIVE, type)
			.ifPresent(Event::inactivate);
	}

	public PollingEventListResponse getActiveEvents(Long userId, Long meetingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		List<Event> events = eventRepository.findByUserIdAndMeetingIdAndStatus(userId, meetingId, ACTIVE);
		events.forEach(Event::inactivateIfExpired);

		List<Event> notExpiredEvents = events.stream()
			.filter(Event::isActive)
			.toList();

		return PollingEventListResponse.from(notExpiredEvents);
	}

	public void inactivateLastEventsOfAllMemberByType(Long meetingId, EventType eventType) {
		List<Event> events = eventRepository.findByMeetingIdAndStatusAndType(meetingId, ACTIVE, eventType);
		events.forEach(Event::inactivate);
	}

	private String constructEventMessage(EventType type, Map<String, String> contentsMap) {
		if (contentsMap.isEmpty()) {
			return null;
		}

		return eventMessageTemplateRepository.findByType(type)
			.map(template -> template.replaceTemplateVariables(contentsMap))
			.orElse(null);
	}

	private Event getEventById(Long eventId) {
		return eventRepository.findById(eventId)
			.orElseThrow(NotFoundEventException::new);
	}
}
