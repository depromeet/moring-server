package org.depromeet.sambad.moring.domain.answer.application;

import org.depromeet.sambad.moring.domain.answer.domain.Answer;
import org.depromeet.sambad.moring.domain.answer.presentation.exception.NotFoundAnswerException;
import org.depromeet.sambad.moring.domain.answer.presentation.request.AnswerRequest;
import org.depromeet.sambad.moring.domain.question.application.QuestionService;
import org.depromeet.sambad.moring.domain.question.domain.Question;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

	private final AnswerRepository answerRepository;

	private final QuestionService questionService;

	@Transactional
	public void saveAnswer(AnswerRequest answerRequest) {
		Question question = questionService.getById(answerRequest.questionId());
		Answer answer = Answer.builder()
			.question(question)
			.content(answerRequest.content())
			.build();
		answerRepository.save(answer);
	}

	public Answer getById(Long questionId, Long answerId) {
		return answerRepository.findByQuestionIdAndAnswerId(questionId, answerId)
			.orElseThrow(NotFoundAnswerException::new);
	}
}