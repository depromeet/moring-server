package org.depromeet.sambad.moring.question.presentation.response;

import java.util.List;

import org.depromeet.sambad.moring.answer.presentation.response.AnswerResponse;
import org.depromeet.sambad.moring.question.domain.Question;

public record QuestionResponse(
	Long questionId,
	String questionImageUrl,
	String title,
	List<AnswerResponse> answers
) {

	public static QuestionResponse from(final Question question) {
		return new QuestionResponse(
			question.getId(),
			question.getQuestionImageUrl(),
			question.getTitle(),
			AnswerResponse.from(question.getAnswers())
		);
	}
}
