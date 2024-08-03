package org.depromeet.sambad.moring.event.application;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.event.domain.Event;
import org.depromeet.sambad.moring.event.domain.EventStatus;

public interface EventRepository {
	void save(Event event);

	Optional<Event> findById(Long eventId);

	List<Event> findByUserIdAndMeetingIdAndStatus(Long userId, Long meetingId, EventStatus status);
}
