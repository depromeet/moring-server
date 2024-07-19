package org.depromeet.sambad.moring.answer.presentation.response;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.sambad.moring.answer.domain.Answer;

public record AnswerResponse(
	Long answerId,
	String content
) {
	public static List<AnswerResponse> from(List<Answer> answers) {
		return answers.stream()
			.map(AnswerResponse::from)
			.collect(Collectors.toUnmodifiableList());
	}

	public static AnswerResponse from(Answer answer) {
		return new AnswerResponse(
			answer.getId(),
			answer.getContent()
		);
	}
}
