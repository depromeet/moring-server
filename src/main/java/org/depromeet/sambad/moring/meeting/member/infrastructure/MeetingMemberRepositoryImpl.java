package org.depromeet.sambad.moring.meeting.member.infrastructure;

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
    public Optional<MeetingMember> findByUserId(Long userId) {
        return meetingMemberJpaRepository.findByUserId(userId);
    }

    @Override
    public Optional<MeetingMember> findById(Long meetingMemberId) {
        return meetingMemberJpaRepository.findById(meetingMemberId);
    }

    @Override
    public boolean isUserExceedingMaxMeetings(Long userId, int maxMeetings) {
        return meetingMemberQueryRepository.isUserExceedingMaxMeetings(userId, maxMeetings);
    }

    @Override
    public void save(MeetingMember meetingMember) {
        meetingMemberJpaRepository.save(meetingMember);
    }

    @Override
    public boolean isMeetingExceedingMaxMembers(Long meetingId, int maxMeetingMembers) {
        return meetingMemberQueryRepository.isMeetingExceedingMaxMembers(meetingId, maxMeetingMembers);
    }

    @Override
    public boolean isHostExceedingMaxMeetings(Long meetingId, int maxHostMeetings) {
        return meetingMemberQueryRepository.isHostExceedingMaxMeetings(meetingId, maxHostMeetings);
    }
}
