package org.depromeet.sambad.moring.meeting.handWaving.infrastructure;

import org.depromeet.sambad.moring.meeting.handWaving.application.HandWavingRepository;
import org.depromeet.sambad.moring.meeting.handWaving.domain.HandWaving;
import org.depromeet.sambad.moring.meeting.handWaving.presentation.exception.NotFoundHandWavingException;
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
	public HandWaving findById(Long handWavingId) {
		return handWavingJpaRepository.findById(handWavingId)
			.orElseThrow(NotFoundHandWavingException::new);
	}
}
