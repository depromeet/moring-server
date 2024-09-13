package org.depromeet.sambad.moring.domain.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import org.depromeet.sambad.moring.domain.question.domain.Question;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuestionTitleResponse(
	@Schema(description = "질문 부제목", example = "제시된 초능력 중에", requiredMode= REQUIRED)
	String subtitle,

	@Schema(description = "질문 제목", example = "갖고 싶은 초능력은?", requiredMode = REQUIRED)
	String mainTitle,

	@Schema(description = "질문 전체 제목", example = "제시된 초능력 중에 갖고 싶은 초능력은?", requiredMode = REQUIRED)
	String fullTitle
) {

	public static QuestionTitleResponse from(Question question) {
		return new QuestionTitleResponse(question.getSubtitle(), question.getTitle(), question.getFullTitle());
	}
}
