package org.depromeet.sambad.moring.question.application;

import org.depromeet.sambad.moring.question.domain.Question;
import org.depromeet.sambad.moring.question.presentation.exception.NotFoundQuestionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

	private final QuestionRepository questionRepository;

	public Question getById(Long id) {
		return questionRepository.findById(id)
			.orElseThrow(NotFoundQuestionException::new);
	}
}
