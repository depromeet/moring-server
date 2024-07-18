package org.depromeet.sambad.moring.meeting.member.infrastructure;

import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberRepository;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MeetingMemberRepositoryImpl implements MeetingMemberRepository {

    private final MeetingMemberJpaRepository meetingMemberJpaRepository;

    @Override
    public Optional<MeetingMember> findByUserId(Long userId) {
        return meetingMemberJpaRepository.findByUserId(userId);
    }

    @Override
    public Optional<MeetingMember> findById(Long meetingMemberId) {
        return meetingMemberJpaRepository.findById(meetingMemberId);
    }
}
