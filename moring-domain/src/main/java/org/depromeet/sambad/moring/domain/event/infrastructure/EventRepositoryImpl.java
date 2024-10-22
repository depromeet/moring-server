package org.depromeet.sambad.moring.domain.event.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.domain.event.application.EventRepository;
import org.depromeet.sambad.moring.domain.event.domain.Event;
import org.depromeet.sambad.moring.domain.event.domain.EventStatus;
import org.depromeet.sambad.moring.domain.event.domain.EventType;
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
	public Optional<Event> findFirstByUserIdAndMeetingIdAndStatusAndType(
		Long userId, Long meetingId, EventStatus eventStatus, EventType type
	) {
		return eventJpaRepository.findFirstByUserIdAndMeetingIdAndStatusAndTypeOrderByIdDesc(
			userId, meetingId, eventStatus, type);
	}

	@Override
	public List<Event> findByMeetingIdAndStatusAndType(Long meetingId, EventStatus eventStatus, EventType eventType) {
		return eventJpaRepository.findByMeetingIdAndStatusAndType(meetingId, eventStatus, eventType);
	}

	@Override
	public List<Event> findByUserIdAndMeetingIdAndCreatedAtAfterOrderByCreatedAtDesc(
		Long userId, Long meetingId, LocalDateTime keepDays
	) {
		return eventJpaRepository.findByUserIdAndMeetingIdAndCreatedAtAfterOrderByCreatedAtDesc(
			userId, meetingId, keepDays);
	}

	@Override
	public void save(Event event) {
		eventJpaRepository.save(event);
	}
}
