package org.depromeet.sambad.moring.meeting.poking.infrastructure;

import org.depromeet.sambad.moring.meeting.poking.domain.Poking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokingJpaRepository extends JpaRepository<Poking, Long> {
}
