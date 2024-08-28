package org.depromeet.sambad.moring.domain.event.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.domain.event.domain.Event;
import org.depromeet.sambad.moring.domain.event.domain.EventStatus;
import org.depromeet.sambad.moring.domain.event.domain.EventType;

public interface EventRepository {
	void save(Event event);

	Optional<Event> findById(Long eventId);

	List<Event> findByUserIdAndMeetingIdAndStatus(Long userId, Long meetingId, EventStatus status);

	Optional<Event> findFirstByUserIdAndMeetingIdAndStatusAndType(
		Long userId, Long meetingId, EventStatus eventStatus, EventType type
	);

	List<Event> findByMeetingIdAndStatusAndType(Long meetingId, EventStatus eventStatus, EventType eventType);

	List<Event> findByUserIdAndMeetingIdAndCreatedAtAfterOrderByCreatedAtDesc(
		Long userId, Long meetingId, LocalDateTime keepDays);
}
