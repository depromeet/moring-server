package org.depromeet.sambad.moring.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import org.depromeet.sambad.moring.file.presentation.annotation.FullFileUrl;

import com.querydsl.core.annotations.QueryProjection;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuestionSummaryResponse(
	@Schema(description = "질문 ID", example = "1", requiredMode = REQUIRED)
	Long questionId,

	@FullFileUrl
	@Schema(description = "질문 이미지 URL", example = "https://example.com", requiredMode = REQUIRED)
	String questionImageFileUrl,

	@Schema(description = "질문 제목", example = "갖고 싶은 초능력은?", requiredMode = REQUIRED)
	String title,

	@Schema(description = "질문 사용 횟수", example = "3", requiredMode = REQUIRED)
	Long usedCount
) {

	@QueryProjection
	public QuestionSummaryResponse {
	}
}
