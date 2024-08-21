package org.depromeet.sambad.moring.meeting.handwaving.infrastructure;

import org.depromeet.sambad.moring.meeting.handwaving.application.HandwavingRepository;
import org.depromeet.sambad.moring.meeting.handwaving.domain.Handwaving;
import org.depromeet.sambad.moring.meeting.handwaving.presentation.exception.NotFoundHandwavingException;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HandwavingRepositoryImpl implements HandwavingRepository {
	private final HandwavingJpaRepository handWavingJpaRepository;

	@Override
	public void save(Handwaving handwaving) {
		handWavingJpaRepository.save(handwaving);
	}

	@Override
	public Handwaving getById(Long handwavingId) {
		return handWavingJpaRepository.findById(handwavingId)
			.orElseThrow(NotFoundHandwavingException::new);
	}
}
