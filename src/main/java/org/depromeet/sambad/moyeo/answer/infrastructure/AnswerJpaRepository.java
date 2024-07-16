package org.depromeet.sambad.moyeo.answer.infrastructure;

import org.depromeet.sambad.moyeo.answer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerJpaRepository extends JpaRepository<Answer, Long> {
}
