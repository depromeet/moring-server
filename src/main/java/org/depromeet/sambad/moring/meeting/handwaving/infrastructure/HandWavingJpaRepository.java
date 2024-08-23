package org.depromeet.sambad.moring.meeting.handwaving.infrastructure;

import java.util.Optional;

import org.depromeet.sambad.moring.meeting.handwaving.domain.HandWaving;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HandWavingJpaRepository extends JpaRepository<HandWaving, Long> {

	Optional<HandWaving> findFirstBySenderIdAndReceiverIdOrderByIdDesc(Long senderMemberId, Long receiverMemberId);
}
