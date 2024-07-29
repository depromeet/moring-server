package org.depromeet.sambad.moring.answer.presentation.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnswerRequest(
	@Schema(description = "질문 ID", example = "1", requiredMode = REQUIRED)
	@NotNull
	Long questionId,

	@Schema(description = "답변 내용", example = "답변 예시입니다.", requiredMode = REQUIRED)
	@NotBlank
	String content
) {
}
