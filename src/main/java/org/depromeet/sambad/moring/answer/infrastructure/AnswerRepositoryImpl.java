package org.depromeet.sambad.moring.answer.infrastructure;

import org.depromeet.sambad.moring.answer.application.AnswerRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AnswerRepositoryImpl implements AnswerRepository {

	private final AnswerJpaRepository answerJpaRepository;
}
