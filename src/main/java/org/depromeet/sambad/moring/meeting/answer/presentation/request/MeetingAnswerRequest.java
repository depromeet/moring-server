package org.depromeet.sambad.moring.meeting.answer.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MeetingAnswerRequest(
	@Schema(example = "1", description = "답변할 모임의 질문 식별자")
	@NotNull
	Long meetingQuestionId,

	@Schema(example = "1", description = "선택한 답변 식별자")
	@NotNull
	Long answerId
) {
}