package org.depromeet.sambad.moring.question.application;

import java.util.Optional;

import org.depromeet.sambad.moring.question.domain.Question;

public interface QuestionRepository {

	Optional<Question> findById(Long id);
}
