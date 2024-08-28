package org.depromeet.sambad.moring.domain.question.infrastructure;

import org.depromeet.sambad.moring.domain.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJpaRepository extends JpaRepository<Question, Long> {
}
