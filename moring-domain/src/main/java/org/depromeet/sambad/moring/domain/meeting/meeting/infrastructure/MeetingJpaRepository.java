package org.depromeet.sambad.moring.domain.meeting.meeting.infrastructure;

import java.util.Optional;

import org.depromeet.sambad.moring.domain.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.domain.meeting.meeting.domain.MeetingCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingJpaRepository extends JpaRepository<Meeting, Long> {

	Optional<Meeting> findByCode(MeetingCode code);
}
