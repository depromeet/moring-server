package org.depromeet.sambad.moring.meeting.member.infrastructure;

import java.util.List;

import org.depromeet.sambad.moring.meeting.member.application.HobbyRepository;
import org.depromeet.sambad.moring.meeting.member.domain.Hobby;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class HobbyRepositoryImpl implements HobbyRepository {

	private final HobbyJpaRepository hobbyJpaRepository;

	@Override
	public List<Hobby> findAll() {
		return hobbyJpaRepository.findAll();
	}

	@Override
	public List<Hobby> findByIdIn(List<Long> ids) {
		return hobbyJpaRepository.findByIdIn(ids);
	}
}
