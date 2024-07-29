package org.depromeet.sambad.moring.answer.application;

import java.util.Optional;

import org.depromeet.sambad.moring.answer.domain.Answer;

public interface AnswerRepository {

	Optional<Answer> findByQuestionIdAndAnswerId(Long questionId, Long answerId);

	void save(Answer answer);
}
