package org.depromeet.sambad.moring.domain.meeting.member.application;

import java.util.List;

import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMemberHobby;

public interface MeetingMemberHobbyRepository {

	void saveAll(List<MeetingMemberHobby> hobbies);

	void deleteAllInBatch(List<MeetingMemberHobby> oldHobbies);
}
