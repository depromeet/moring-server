package org.depromeet.sambad.moring.domain.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.domain.common.response.PageableResponse;
import org.depromeet.sambad.moring.domain.question.domain.QuestionSummaryDto;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuestionListResponse<T>(
	@Schema(
		description = "질문 목록",
		example = "[{\"questionId\":1,\"questionImageFileUrl\":\"https://example.com\",\"title\":\"갖고 싶은 초능력은?\",\"usedCount\":3}]",
		requiredMode = REQUIRED
	)
	List<QuestionSummaryResponse> contents,

	@Schema(description = "페이징 정보", requiredMode = REQUIRED)
	PageableResponse<T> pageable
) {
	public static <T> QuestionListResponse<T> of(
		List<QuestionSummaryDto> dtoList, PageableResponse<T> pageableResponse
	) {
		List<QuestionSummaryResponse> contents = dtoList.stream()
			.map(QuestionSummaryResponse::from)
			.toList();
		return new QuestionListResponse(contents, pageableResponse);
	}
}
