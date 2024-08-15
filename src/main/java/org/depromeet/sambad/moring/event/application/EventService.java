package org.depromeet.sambad.moring.event.application;

import static org.depromeet.sambad.moring.event.domain.EventStatus.*;

import java.util.List;

import org.depromeet.sambad.moring.event.domain.Event;
import org.depromeet.sambad.moring.event.domain.EventType;
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
public class EventService {
	private final EventRepository eventRepository;
	private final MeetingMemberValidator meetingMemberValidator;

	@Transactional
	public void publish(Long userId, Long meetingId, EventType type) {
		if (meetingMemberValidator.isNotMemberOfMeeting(userId, meetingId)) {
			log.warn("User is not member of meeting. userId: {}, meetingId: {}", userId, meetingId);
			return;
		}

		Event event = Event.publish(userId, meetingId, type);
		eventRepository.save(event);
	}

	@Transactional
	public void inactivate(Long eventId) {
		Event event = getEventById(eventId);
		event.inactivate();
		eventRepository.save(event);
	}

	@Transactional
	public void inactivateLastEventByType(Long userId, Long meetingId, EventType type) {
		eventRepository.findFirstByUserIdAndMeetingIdAndStatusAndType(userId, meetingId, ACTIVE, type)
			.ifPresent(Event::inactivate);
	}

	@Transactional
	public PollingEventListResponse getActiveEvents(Long userId, Long meetingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		List<Event> events = eventRepository.findByUserIdAndMeetingIdAndStatus(userId, meetingId, ACTIVE);
		events.forEach(Event::inactivateIfExpired);

		List<Event> notExpiredEvents = events.stream()
			.filter(Event::isActive)
			.toList();

		return PollingEventListResponse.from(notExpiredEvents);
	}

	@Transactional
	public void inactivateLastEventsOfAllMemberByType(Long meetingId, EventType eventType) {
		List<Event> events = eventRepository.findByMeetingIdAndStatusAndType(meetingId, ACTIVE, eventType);
		events.forEach(Event::inactivate);
	}

	private Event getEventById(Long eventId) {
		return eventRepository.findById(eventId)
			.orElseThrow(NotFoundEventException::new);
	}
}
