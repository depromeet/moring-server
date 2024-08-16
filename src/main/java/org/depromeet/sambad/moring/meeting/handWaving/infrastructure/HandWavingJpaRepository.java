package org.depromeet.sambad.moring.meeting.handWaving.infrastructure;

import org.depromeet.sambad.moring.meeting.handWaving.domain.HandWaving;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HandWavingJpaRepository extends JpaRepository<HandWaving, Long> {
}
