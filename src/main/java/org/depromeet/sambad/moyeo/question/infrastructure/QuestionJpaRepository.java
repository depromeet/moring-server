package org.depromeet.sambad.moyeo.question.infrastructure;

import org.depromeet.sambad.moyeo.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJpaRepository extends JpaRepository<Question, Long> {
}
