package org.depromeet.sambad.moring.answer.application;

import org.depromeet.sambad.moring.answer.domain.Answer;
import org.depromeet.sambad.moring.answer.presentation.exception.NotFoundAnswerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

	private final AnswerRepository answerRepository;

	public Answer getById(Long questionId, Long answerId) {
		return answerRepository.findByQuestionIdAndAnswerId(questionId, answerId)
			.orElseThrow(NotFoundAnswerException::new);
	}
}