package org.depromeet.sambad.moyeo.question.infrastructure;

import org.depromeet.sambad.moyeo.question.application.QuestionRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {

	private final QuestionJpaRepository questionJpaRepository;
}
