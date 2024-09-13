package org.depromeet.sambad.moring.domain.meeting.handwaving.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import org.depromeet.sambad.moring.domain.meeting.handwaving.domain.HandWaving;
import org.depromeet.sambad.moring.domain.meeting.handwaving.domain.HandWavingStatus;

import io.swagger.v3.oas.annotations.media.Schema;

public record HandWavingStatusResponse(

	@Schema(description = "손 흔들기 ID", example = "1", requiredMode = NOT_REQUIRED)
	Long HandWavingId,

	@Schema(description = "손 흔들기 상태", example = "REQUESTED", requiredMode = REQUIRED)
	HandWavingStatus status,

	@Schema(description = "요청 유저가 Sender인지에 대한 여부", example = "true", requiredMode = REQUIRED)
	boolean isSender
) {
	public static HandWavingStatusResponse of(HandWavingStatus status) {
		return new HandWavingStatusResponse(null, status, false);
	}

	public static HandWavingStatusResponse from(HandWaving handWaving) {
		return new HandWavingStatusResponse(handWaving.getId(), handWaving.getStatus(), handWaving.isSender());
	}
}
