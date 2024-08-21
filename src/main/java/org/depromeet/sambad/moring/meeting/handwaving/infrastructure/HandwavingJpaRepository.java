package org.depromeet.sambad.moring.meeting.handwaving.infrastructure;

import org.depromeet.sambad.moring.meeting.handwaving.domain.Handwaving;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HandwavingJpaRepository extends JpaRepository<Handwaving, Long> {
}
