package org.depromeet.sambad.moring.domain.auth.domain;

public record LoginResult(
	String accessToken,
	String refreshToken,
	boolean isNewUser,
	boolean isNotCompletedOnboarding,
	Long userId
) {
}
