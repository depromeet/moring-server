package org.depromeet.sambad.moring.meeting.member.application;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;

import java.util.Optional;

public interface MeetingMemberRepository {
    Optional<MeetingMember> findByUserId(Long userId);

    Optional<MeetingMember> findById(Long meetingMemberId);
}
