package org.depromeet.sambad.moring.domain.answer.infrastructure;

import java.util.Optional;

import org.depromeet.sambad.moring.domain.answer.application.AnswerRepository;
import org.depromeet.sambad.moring.domain.answer.domain.Answer;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AnswerRepositoryImpl implements AnswerRepository {

	private final AnswerJpaRepository answerJpaRepository;

	@Override
	public Optional<Answer> findByQuestionIdAndAnswerId(Long questionId, Long answerId) {
		return answerJpaRepository.findByIdAndQuestionId(answerId, questionId);
	}

	@Override
	public void save(Answer answer) {
		answerJpaRepository.save(answer);
	}
}
