package org.depromeet.sambad.moring.meeting.handwaving.presentation.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record HandWavingRequest(
	@Schema(description = "손 흔들어 인사할 모임원 ID", example = "1", requiredMode = REQUIRED)
	@NotNull
	Long receiverMemberId
) {
}
