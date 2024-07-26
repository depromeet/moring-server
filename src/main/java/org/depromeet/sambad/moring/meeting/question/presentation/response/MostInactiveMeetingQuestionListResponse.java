package org.depromeet.sambad.moring.meeting.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record MostInactiveMeetingQuestionListResponse(
	@Schema(
		description = "가장 활동이 적은 모임의 질문 목록",
		example = "[{\"id\":1,\"title\":\"갖고 싶은 초능력은?\",\"content\":순간이동,\"engagementRate\":\"70\"}]",
		requiredMode = REQUIRED

	)
	List<MostInactiveMeetingQuestionListResponseDetail> content
) {

	public static MostInactiveMeetingQuestionListResponse from(
		List<MostInactiveMeetingQuestionListResponseDetail> responseDetails
	) {
		return new MostInactiveMeetingQuestionListResponse(responseDetails);
	}
}
