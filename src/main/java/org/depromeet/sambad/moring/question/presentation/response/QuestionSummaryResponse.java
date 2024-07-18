package org.depromeet.sambad.moring.question.presentation.response;

import com.querydsl.core.annotations.QueryProjection;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuestionSummaryResponse(
	@Schema(example = "1")
	Long questionId,

	@Schema(example = "https://avatars.githubusercontent.com/u/173370739?v=4")
	String questionImageFileUrl,

	@Schema(example = "국내 여행지 중에서 추천하고 싶은 곳은?")
	String title,

	@Schema(example = "18")
	int usedCount
) {

	@QueryProjection
	public QuestionSummaryResponse {
	}
}
