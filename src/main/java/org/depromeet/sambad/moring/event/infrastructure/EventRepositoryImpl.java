package org.depromeet.sambad.moring.event.infrastructure;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.event.application.EventRepository;
import org.depromeet.sambad.moring.event.domain.Event;
import org.depromeet.sambad.moring.event.domain.EventStatus;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {
	private final EventJpaRepository eventJpaRepository;

	@Override
	public Optional<Event> findById(Long eventId) {
		return eventJpaRepository.findById(eventId);
	}

	@Override
	public List<Event> findByUserIdAndMeetingIdAndStatus(Long userId, Long meetingId, EventStatus status) {
		return eventJpaRepository.findByUserIdAndMeetingIdAndStatus(userId, meetingId, status);
	}

	@Override
	public void save(Event event) {
		eventJpaRepository.save(event);
	}
}