package org.depromeet.sambad.moring.meeting.meeting.infrastructure;

import java.util.Optional;

import org.depromeet.sambad.moring.meeting.meeting.application.MeetingRepository;
import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.meeting.domain.MeetingCode;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MeetingRepositoryImpl implements MeetingRepository {

	private final MeetingJpaRepository meetingJpaRepository;

	@Override
	public Optional<Meeting> findById(Long id) {
		return meetingJpaRepository.findById(id);
	}

	@Override
	public void save(Meeting meeting) {
		meetingJpaRepository.save(meeting);
	}

	@Override
	public Optional<Meeting> findByCode(MeetingCode code) {
		return meetingJpaRepository.findByCode(code);
	}
}
