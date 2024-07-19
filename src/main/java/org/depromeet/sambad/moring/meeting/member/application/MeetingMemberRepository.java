package org.depromeet.sambad.moring.meeting.member.application;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;

public interface MeetingMemberRepository {
	Optional<MeetingMember> findByUserId(Long userId);

	Optional<MeetingMember> findById(Long meetingMemberId);

	List<MeetingMember> findNextTargetsByMeeting(Long meetingId, Long loginMeetingMemberId);

	boolean isUserExceedingMaxMeetings(Long userId, int maxMeetings);

	void save(MeetingMember meetingMember);

	boolean isMeetingExceedingMaxMembers(Long meetingId, int maxMeetingMembers);

	boolean isHostExceedingMaxMeetings(Long meetingId, int maxHostMeetings);

	List<MeetingMember> findByMeetingIdOrderByName(Long meetingId);

	boolean isUserMemberOfMeeting(Long userId, Long meetingId);
}
