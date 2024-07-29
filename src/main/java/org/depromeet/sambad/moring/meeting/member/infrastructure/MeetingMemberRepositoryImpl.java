package org.depromeet.sambad.moring.meeting.member.infrastructure;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberRepository;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MeetingMemberRepositoryImpl implements MeetingMemberRepository {

	private final MeetingMemberJpaRepository meetingMemberJpaRepository;
	private final MeetingMemberQueryRepository meetingMemberQueryRepository;

	@Override
	public void save(MeetingMember meetingMember) {
		meetingMemberJpaRepository.save(meetingMember);
	}

	@Override
	public Optional<MeetingMember> findById(Long meetingMemberId) {
		return meetingMemberJpaRepository.findById(meetingMemberId);
	}

	@Override
	public List<MeetingMember> findByUserId(Long userId) {
		return meetingMemberJpaRepository.findByUserId(userId);
	}

	@Override
	public Optional<MeetingMember> findByUserIdAndMeetingId(Long userId, Long meetingId) {
		return meetingMemberJpaRepository.findByUserIdAndMeetingId(userId, meetingId);
	}

	@Override
	public List<MeetingMember> findNextTargetsByMeeting(Long meetingId, Long loginMeetingMemberId,
		List<Long> excludeMemberIds) {
		return meetingMemberQueryRepository.findNextTargetsByMeetingId(meetingId, loginMeetingMemberId,
			excludeMemberIds);
	}

	@Override
	public boolean isUserExceedingMaxMeetings(Long userId, int maxMeetings) {
		return meetingMemberQueryRepository.isUserExceedingMaxMeetings(userId, maxMeetings);
	}

	@Override
	public boolean isMeetingExceedingMaxMembers(Long meetingId, int maxMeetingMembers) {
		return meetingMemberQueryRepository.isMeetingExceedingMaxMembers(meetingId, maxMeetingMembers);
	}

	@Override
	public boolean isOwnerExceedingMaxMeetings(Long meetingId, int maxHostMeetings) {
		return meetingMemberQueryRepository.isOwnerExceedingMaxMeetings(meetingId, maxHostMeetings);
	}

	@Override
	public List<MeetingMember> findByMeetingIdOrderByName(Long meetingId) {
		return meetingMemberJpaRepository.findByMeetingIdOrderByName(meetingId);
	}

	@Override
	public boolean isUserMemberOfMeeting(Long userId, Long meetingId) {
		return meetingMemberQueryRepository.isUserMemberOfMeeting(userId, meetingId);
	}
}
