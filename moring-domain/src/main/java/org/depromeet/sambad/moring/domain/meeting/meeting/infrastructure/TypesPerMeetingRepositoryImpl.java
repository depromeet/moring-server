package org.depromeet.sambad.moring.domain.meeting.meeting.infrastructure;

import java.util.List;

import org.depromeet.sambad.moring.domain.meeting.meeting.application.TypesPerMeetingRepository;
import org.depromeet.sambad.moring.domain.meeting.meeting.domain.TypesPerMeeting;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class TypesPerMeetingRepositoryImpl implements TypesPerMeetingRepository {

	private final TypesPerMeetingJpaRepository typesPerMeetingJpaRepository;

	@Override
	public void saveAll(List<TypesPerMeeting> types) {
		typesPerMeetingJpaRepository.saveAll(types);
	}
}
