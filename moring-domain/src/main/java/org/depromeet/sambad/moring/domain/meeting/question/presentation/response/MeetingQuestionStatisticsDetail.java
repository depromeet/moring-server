package org.depromeet.sambad.moring.domain.meeting.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingQuestionStatisticsDetail(
	@Schema(description = "선택된 수 기반 순위", example = "1", requiredMode = REQUIRED)
	int rank,

	@Schema(description = "답변 내용", example = "💩 똥", requiredMode = REQUIRED)
	String answerContent,

	@Schema(description = "해당 답변을 선택한 모임원 수", example = "14", requiredMode = REQUIRED)
	int count,

	@Schema(description = "해당 답변을 선택한 모임원 비율", example = "50", requiredMode = REQUIRED)
	int percentage
) {
}
