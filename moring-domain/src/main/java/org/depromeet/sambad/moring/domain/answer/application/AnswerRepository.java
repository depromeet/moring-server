package org.depromeet.sambad.moring.domain.answer.application;

import java.util.Optional;

import org.depromeet.sambad.moring.domain.answer.domain.Answer;

public interface AnswerRepository {

	void save(Answer answer);

	Optional<Answer> findByQuestionIdAndAnswerId(Long questionId, Long answerId);
}
