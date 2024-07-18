package org.depromeet.sambad.moring.meeting.member.infrastructure;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingMemberJpaRepository extends JpaRepository<MeetingMember, Long> {

    Optional<MeetingMember> findByUserId(Long userId);
}
