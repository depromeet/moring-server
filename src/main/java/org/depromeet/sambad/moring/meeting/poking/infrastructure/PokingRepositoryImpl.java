package org.depromeet.sambad.moring.meeting.poking.infrastructure;

import org.depromeet.sambad.moring.meeting.poking.application.PokingRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PokingRepositoryImpl implements PokingRepository {
	private final PokingJpaRepository pokingJpaRepository;
}
