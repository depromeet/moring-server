package org.depromeet.sambad.moring.meeting.member.infrastructure;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingMemberJpaRepository extends JpaRepository<MeetingMember, Long> {

    Optional<MeetingMember> findByUserId(Long userId);

    List<MeetingMember> findByMeetingIdOrderByName(Long meetingId);

    Optional<MeetingMember> findByUserIdAndMeetingId(Long userId, Long meetingId);
}
