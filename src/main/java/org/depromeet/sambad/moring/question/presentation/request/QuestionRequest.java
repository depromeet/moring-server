package org.depromeet.sambad.moring.question.presentation.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestionRequest(
	@Schema(description = "질문 제목", example = "오늘의 질문은?", requiredMode = REQUIRED)
	@NotBlank
	String title,

	@Schema(description = "질문 이미지 URL", example = "https://sambad.s3.ap-northeast-2.amazonaws.com/2021/10/13/1634090916_1.jpg", requiredMode = REQUIRED)
	@NotNull
	String questionImageUrl

) {
}
