package org.depromeet.sambad.moring.domain.meeting.member.infrastructure;

import java.util.List;

import org.depromeet.sambad.moring.domain.meeting.member.application.MeetingMemberHobbyRepository;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMemberHobby;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MeetingMemberHobbyRepositoryImpl implements MeetingMemberHobbyRepository {

	private final MeetingMemberHobbyJpaRepository meetingMemberHobbyJpaRepository;

	@Override
	public void saveAll(List<MeetingMemberHobby> hobbies) {
		meetingMemberHobbyJpaRepository.saveAll(hobbies);
	}

	@Override
	public void deleteAllInBatch(List<MeetingMemberHobby> oldHobbies) {
		meetingMemberHobbyJpaRepository.deleteAllInBatch(oldHobbies);
	}
}
