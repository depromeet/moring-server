package org.depromeet.sambad.moring.domain.meeting.handwaving.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import org.depromeet.sambad.moring.domain.meeting.handwaving.domain.HandWavingStatus;

import io.swagger.v3.oas.annotations.media.Schema;

public record HandWavingStatusResponse(

	@Schema(description = "손 흔들기 ID", example = "1", requiredMode = NOT_REQUIRED)
	Long HandWavingId,

	@Schema(description = "손 흔들기 상태", example = "REQUESTED", requiredMode = REQUIRED)
	HandWavingStatus status
) {
	public static HandWavingStatusResponse of(HandWavingStatus status) {
		return new HandWavingStatusResponse(null, status);
	}

	public static HandWavingStatusResponse of(Long handWavingId, HandWavingStatus status) {
		return new HandWavingStatusResponse(handWavingId, status);
	}
}
