package org.depromeet.sambad.moring.domain.event.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.domain.event.domain.Event;
import org.depromeet.sambad.moring.domain.event.domain.EventStatus;
import org.depromeet.sambad.moring.domain.event.domain.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<Event, Long> {
	List<Event> findByUserIdAndMeetingIdAndStatus(Long userId, Long meetingId, EventStatus status);

	Optional<Event> findFirstByUserIdAndMeetingIdAndStatusAndTypeOrderByIdDesc(
		Long userId, Long meetingId, EventStatus eventStatus, EventType type
	);

	List<Event> findByMeetingIdAndStatusAndType(Long meetingId, EventStatus eventStatus, EventType eventType);

	List<Event> findByUserIdAndMeetingIdAndCreatedAtAfterOrderByCreatedAtDesc(
		Long userId, Long meetingId, LocalDateTime keepDays);
}
