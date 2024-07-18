package org.depromeet.sambad.moring.question.presentation.response;

import java.util.List;

import org.depromeet.sambad.moring.common.response.PageableResponse;

public record QuestionListResponse(
	List<QuestionSummaryResponse> content,
	PageableResponse pageable
) {
	public static QuestionListResponse of(List<QuestionSummaryResponse> content, PageableResponse pageableResponse) {
		return new QuestionListResponse(content, pageableResponse);
	}
}
