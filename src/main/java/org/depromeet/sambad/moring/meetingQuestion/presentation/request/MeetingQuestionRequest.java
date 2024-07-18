package org.depromeet.sambad.moring.meetingQuestion.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MeetingQuestionRequest(
	@Schema(example = "1", description = "질문 리스트에서 등록하고 싶은 질문의 식별자")
	@NotNull
	Long questionId
) {
}