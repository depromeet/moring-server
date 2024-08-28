package org.depromeet.sambad.moring.domain.meeting.answer.presentation.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MeetingAnswerRequest(
	@Schema(description = "선택한 Answer ID 리스트", example = "[1, 2, 3]", requiredMode = REQUIRED)
	@Size(max = 9)
	@NotNull
	List<Long> answerIds
) {
}