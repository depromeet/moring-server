package org.depromeet.sambad.moring.domain.meeting.meeting.infrastructure;

import java.util.List;
import java.util.Set;

import org.depromeet.sambad.moring.domain.meeting.meeting.domain.MeetingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingTypeJpaRepository extends JpaRepository<MeetingType, Long> {

	Set<MeetingType> findByIdIn(List<Long> ids);
}
