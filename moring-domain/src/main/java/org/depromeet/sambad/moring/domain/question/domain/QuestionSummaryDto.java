package org.depromeet.sambad.moring.domain.question.domain;

import com.querydsl.core.annotations.QueryProjection;

public record QuestionSummaryDto(
	Question question,
	Long usedCount
) {

	@QueryProjection
	public QuestionSummaryDto {
	}
}
