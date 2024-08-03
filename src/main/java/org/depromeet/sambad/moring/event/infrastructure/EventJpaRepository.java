package org.depromeet.sambad.moring.event.infrastructure;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.event.domain.Event;
import org.depromeet.sambad.moring.event.domain.EventStatus;
import org.depromeet.sambad.moring.event.domain.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<Event, Long> {
	List<Event> findByUserIdAndMeetingIdAndStatus(Long userId, Long meetingId, EventStatus status);

	Optional<Event> findFirstByUserIdAndMeetingIdAndStatusAndTypeOrderByIdDesc(
		Long userId, Long meetingId, EventStatus eventStatus, EventType type
	);
}
