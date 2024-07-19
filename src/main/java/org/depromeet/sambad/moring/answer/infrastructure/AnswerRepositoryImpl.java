package org.depromeet.sambad.moring.answer.infrastructure;

import java.util.Optional;

import org.depromeet.sambad.moring.answer.application.AnswerRepository;
import org.depromeet.sambad.moring.answer.domain.Answer;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AnswerRepositoryImpl implements AnswerRepository {

	private final AnswerJpaRepository answerJpaRepository;

	@Override
	public Optional<Answer> findById(Long id) {
		return answerJpaRepository.findById(id);
	}
}
