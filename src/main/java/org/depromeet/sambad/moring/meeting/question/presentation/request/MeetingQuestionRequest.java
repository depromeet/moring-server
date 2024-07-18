package org.depromeet.sambad.moring.meeting.question.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MeetingQuestionRequest(
	@Schema(example = "1", description = "다음 릴레이 질문")
	@NotNull
	Long questionId,

	@Schema(example = "1", description = "다음 릴레이 질문 대상자")
	@NotNull
	Long meetingMemberId
) {
}