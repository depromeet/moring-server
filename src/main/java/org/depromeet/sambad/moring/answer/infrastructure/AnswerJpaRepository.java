package org.depromeet.sambad.moring.answer.infrastructure;

import java.util.Optional;

import org.depromeet.sambad.moring.answer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerJpaRepository extends JpaRepository<Answer, Long> {

	Optional<Answer> findByIdAndQuestionId(Long id, Long questionId);
}