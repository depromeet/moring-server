package org.depromeet.sambad.moring.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.common.response.PageableResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuestionListResponse<T>(
	@Schema(
		description = "질문 목록",
		example = "[{\"questionId\":1,\"questionImageFileUrl\":\"https://example.com\",\"title\":\"갖고 싶은 초능력은?\",\"usedCount\":3}]",
		requiredMode = REQUIRED
	)
	List<QuestionSummaryResponse> content,

	@Schema(description = "페이징 정보", requiredMode = REQUIRED)
	PageableResponse<T> pageable
) {
	public static <T> QuestionListResponse<T> of(List<QuestionSummaryResponse> content,
		PageableResponse<T> pageableResponse) {
		return new QuestionListResponse(content, pageableResponse);
	}
}
