package org.depromeet.sambad.moring.meeting.handwaving.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import org.depromeet.sambad.moring.meeting.handwaving.domain.HandWaving;
import org.depromeet.sambad.moring.meeting.handwaving.domain.HandWavingStatus;

import io.swagger.v3.oas.annotations.media.Schema;

public record HandWavingStatusResponse(

	@Schema(description = "손 흔들기 ID", example = "1", requiredMode = REQUIRED)
	Long HandWavingId,

	@Schema(description = "손 흔들기 상태", example = "REQUESTED", requiredMode = REQUIRED)
	HandWavingStatus status
) {
	public static HandWavingStatusResponse from(HandWaving handWaving) {
		return new HandWavingStatusResponse(
			handWaving.getId(),
			handWaving.getStatus()
		);
	}
}
