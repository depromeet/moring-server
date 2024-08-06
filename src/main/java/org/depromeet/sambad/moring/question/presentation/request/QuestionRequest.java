package org.depromeet.sambad.moring.question.presentation.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.question.domain.QuestionType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestionRequest(
	@Schema(description = "질문 제목", example = "오늘의 질문은?", requiredMode = REQUIRED)
	@NotBlank
	String title,

	@Schema(description = "질문 이미지 URL", example = "https://sambad.s3.ap-northeast-2.amazonaws.com/2021/10/13/1634090916_1.jpg", requiredMode = REQUIRED)
	@NotNull
	String questionImageUrl,

	@Schema(description = "질문 유형", example = "MULTIPLE_SHORT_CHOICE", requiredMode = REQUIRED)
	@NotNull
	QuestionType questionType,

	@Schema(
		description = "답변 내용",
		example = "[\"답변1 예시입니다.\", \"답변2 예시입니다.\", \"답변3 예시입니다.\"]",
		requiredMode = REQUIRED
	)
	@NotNull
	List<String> answerContents
) {
}
