package org.depromeet.sambad.moring.question.infrastructure;

import org.depromeet.sambad.moring.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJpaRepository extends JpaRepository<Question, Long> {
}
