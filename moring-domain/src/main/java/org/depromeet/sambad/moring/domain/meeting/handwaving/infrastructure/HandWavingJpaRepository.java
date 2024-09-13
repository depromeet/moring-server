package org.depromeet.sambad.moring.domain.meeting.handwaving.infrastructure;

import java.util.List;

import org.depromeet.sambad.moring.domain.meeting.handwaving.domain.HandWaving;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HandWavingJpaRepository extends JpaRepository<HandWaving, Long> {

	List<HandWaving> findAllByEventIdIn(List<Long> eventIds);

}
