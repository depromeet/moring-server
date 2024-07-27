package org.depromeet.sambad.moring.meeting.answer.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.sambad.moring.answer.domain.Answer;

import io.swagger.v3.oas.annotations.media.Schema;

public record AnswerResponse(
	@Schema(description = "답변 ID", example = "1", requiredMode = REQUIRED)
	Long answerId,

	@Schema(description = "답변 내용", example = "분신술", requiredMode = REQUIRED)
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
