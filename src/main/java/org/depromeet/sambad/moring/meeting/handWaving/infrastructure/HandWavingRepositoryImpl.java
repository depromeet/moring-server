package org.depromeet.sambad.moring.meeting.handWaving.infrastructure;

import org.depromeet.sambad.moring.meeting.handWaving.application.HandWavingRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HandWavingRepositoryImpl implements HandWavingRepository {
	private final HandWavingJpaRepository handWavingJpaRepository;
}
