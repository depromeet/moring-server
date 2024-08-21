package org.depromeet.sambad.moring.meeting.handwaving.infrastructure;

import java.util.Optional;

import org.depromeet.sambad.moring.meeting.handwaving.application.HandWavingRepository;
import org.depromeet.sambad.moring.meeting.handwaving.domain.HandWaving;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HandWavingRepositoryImpl implements HandWavingRepository {
	private final HandWavingJpaRepository handWavingJpaRepository;

	@Override
	public void save(HandWaving handWaving) {
		handWavingJpaRepository.save(handWaving);
	}

	@Override
	public Optional<HandWaving> findById(Long handWavingId) {
		return handWavingJpaRepository.findById(handWavingId);
	}

	@Override
	public Optional<HandWaving> findFirstBySenderIdAndReceiverIdDesc(Long senderMemberId, Long receiverMemberId) {
		return handWavingJpaRepository.findFirstBySenderIdAndReceiverIdDesc(senderMemberId, receiverMemberId);
	}
}
