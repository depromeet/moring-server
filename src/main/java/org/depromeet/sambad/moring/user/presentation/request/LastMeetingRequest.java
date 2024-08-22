package org.depromeet.sambad.moring.user.presentation.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import io.swagger.v3.oas.annotations.media.Schema;

public record LastMeetingRequest(
	@Schema(description = "마지막으로 접속한 모임의 ID", example = "1", requiredMode = REQUIRED)
	Long meetingId
) {
}
