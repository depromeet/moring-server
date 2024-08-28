package org.depromeet.sambad.moring.domain.meeting.meeting.infrastructure;

import org.depromeet.sambad.moring.domain.meeting.meeting.domain.TypesPerMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypesPerMeetingJpaRepository extends JpaRepository<TypesPerMeeting, Long> {
}
