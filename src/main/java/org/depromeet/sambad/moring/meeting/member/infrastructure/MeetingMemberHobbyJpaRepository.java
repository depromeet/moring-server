package org.depromeet.sambad.moring.meeting.member.infrastructure;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberHobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingMemberHobbyJpaRepository extends JpaRepository<MeetingMemberHobby, Long> {
}
