package org.depromeet.sambad.moring.question.presentation.response;

import java.util.List;

import org.depromeet.sambad.moring.common.response.PageableResponse;

public record QuestionListResponse<T>(
	List<QuestionSummaryResponse> content,
	PageableResponse<T> pageable
) {
	public static <T> QuestionListResponse<T> of(List<QuestionSummaryResponse> content,
		PageableResponse<T> pageableResponse) {
		return new QuestionListResponse(content, pageableResponse);
	}
}
