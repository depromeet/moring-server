package org.depromeet.sambad.moring.domain.meeting.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingQuestionStatisticsResponse(
	@Schema(description = "질문 통계 상세 리스트", requiredMode = REQUIRED)
	List<MeetingQuestionStatisticsDetail> contents
) {
	public static MeetingQuestionStatisticsResponse of(List<MeetingQuestionStatisticsDetail> statistics) {
		return new MeetingQuestionStatisticsResponse(statistics);
	}
}
