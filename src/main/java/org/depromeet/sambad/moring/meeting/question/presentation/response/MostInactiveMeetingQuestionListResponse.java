package org.depromeet.sambad.moring.meeting.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record MostInactiveMeetingQuestionListResponse(
	@Schema(description = "가장 활동이 적은 모임의 질문 목록", requiredMode = REQUIRED)
	List<MostInactiveMeetingQuestionListResponseDetail> contents
) {

	public static MostInactiveMeetingQuestionListResponse from(
		List<MostInactiveMeetingQuestionListResponseDetail> responseDetails
	) {
		return new MostInactiveMeetingQuestionListResponse(responseDetails);
	}
}
