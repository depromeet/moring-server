package org.depromeet.sambad.moring.event.application;

import java.util.List;

import org.depromeet.sambad.moring.event.domain.Event;
import org.depromeet.sambad.moring.event.domain.EventStatus;
import org.depromeet.sambad.moring.event.domain.EventType;
import org.depromeet.sambad.moring.event.presentation.excepiton.NotFoundEventException;
import org.depromeet.sambad.moring.event.presentation.response.PollingEventListResponse;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
	private final EventRepository eventRepository;
	private final MeetingMemberValidator meetingMemberValidator;

	@Transactional
	public void save(Long userId, Long meetingId, EventType type) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		Event event = Event.publish(userId, meetingId, type);
		eventRepository.save(event);
	}

	public PollingEventListResponse getActiveEvents(Long userId, Long meetingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		List<Event> events = eventRepository.findByUserIdAndMeetingIdAndStatus(userId, meetingId, EventStatus.ACTIVE);
		return PollingEventListResponse.from(events);
	}

	@Transactional
	public void inactivate(Long eventId) {
		Event event = getEventById(eventId);
		event.inactivate();
		eventRepository.save(event);
	}

	private Event getEventById(Long eventId) {
		return eventRepository.findById(eventId)
			.orElseThrow(NotFoundEventException::new);
	}
}
