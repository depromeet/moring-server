package org.depromeet.sambad.moring.domain.user.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import org.depromeet.sambad.moring.domain.user.domain.User;

import io.swagger.v3.oas.annotations.media.Schema;

public record OnboardingResponse(
	@Schema(description = "모임에 하나도 가입되어 있지 않다면 true", example = "false", requiredMode = REQUIRED)
	boolean isNotEnteredAnyMeeting
) {

	public static OnboardingResponse from(User user) {
		return new OnboardingResponse(user.isNotEnteredAnyMeeting());
	}
}
