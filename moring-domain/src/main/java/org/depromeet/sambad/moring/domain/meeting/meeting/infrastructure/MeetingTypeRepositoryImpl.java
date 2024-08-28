package org.depromeet.sambad.moring.domain.meeting.meeting.infrastructure;

import java.util.List;
import java.util.Set;

import org.depromeet.sambad.moring.domain.meeting.meeting.application.MeetingTypeRepository;
import org.depromeet.sambad.moring.domain.meeting.meeting.domain.MeetingType;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MeetingTypeRepositoryImpl implements MeetingTypeRepository {

	private final MeetingTypeJpaRepository meetingTypeJpaRepository;

	@Override
	public List<MeetingType> findAll() {
		return meetingTypeJpaRepository.findAll();
	}

	@Override
	public Set<MeetingType> findByIdIn(List<Long> ids) {
		return meetingTypeJpaRepository.findByIdIn(ids);
	}
}
