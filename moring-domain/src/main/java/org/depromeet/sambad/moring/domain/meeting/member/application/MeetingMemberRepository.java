package org.depromeet.sambad.moring.domain.meeting.member.application;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;

public interface MeetingMemberRepository {
	void save(MeetingMember meetingMember);

	Optional<MeetingMember> findById(Long meetingMemberId);

	List<MeetingMember> findByUserId(Long userId);

	Optional<MeetingMember> findByUserIdAndMeetingId(Long userId, Long meetingId);

	List<MeetingMember> findByMeetingIdAndMeetingMemberIdNotInOrderByName(
		Long meetingId, List<Long> excludeMemberIds);

	List<MeetingMember> findNextTargetsByMeeting(Long meetingId, Long loginMeetingMemberId,
		List<Long> excludeMemberIds);

	boolean isUserExceedingMaxMeetings(Long userId, int maxMeetings);

	boolean isMeetingExceedingMaxMembers(Long meetingId, int maxMeetingMembers);

	boolean isOwnerExceedingMaxMeetings(Long meetingId, int maxHostMeetings);

	boolean isUserMemberOfMeeting(Long userId, Long meetingId);

	boolean isCountOfMembersIsOne(Long meetingId);
}
