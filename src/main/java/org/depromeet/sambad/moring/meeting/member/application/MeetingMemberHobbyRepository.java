package org.depromeet.sambad.moring.meeting.member.application;

import java.util.List;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberHobby;

public interface MeetingMemberHobbyRepository {

	void saveAll(List<MeetingMemberHobby> hobbies);
}
