package org.depromeet.sambad.moring.question.infrastructure;

import org.depromeet.sambad.moring.question.application.QuestionRepository;
import org.depromeet.sambad.moring.question.domain.Question;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {

	private final QuestionJpaRepository questionJpaRepository;

	@Override
	public Optional<Question> findById(Long id) {
		return questionJpaRepository.findById(id);
	}
}
