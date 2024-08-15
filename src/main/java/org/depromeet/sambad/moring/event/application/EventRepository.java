package org.depromeet.sambad.moring.event.application;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.event.domain.Event;
import org.depromeet.sambad.moring.event.domain.EventStatus;
import org.depromeet.sambad.moring.event.domain.EventType;

public interface EventRepository {
	void save(Event event);

	Optional<Event> findById(Long eventId);

	List<Event> findByUserIdAndMeetingIdAndStatus(Long userId, Long meetingId, EventStatus status);

	Optional<Event> findFirstByUserIdAndMeetingIdAndStatusAndType(
		Long userId, Long meetingId, EventStatus eventStatus, EventType type
	);

	List<Event> findByMeetingIdAndStatusAndType(Long meetingId, EventStatus eventStatus, EventType eventType);
}
