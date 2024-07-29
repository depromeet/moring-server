package org.depromeet.sambad.moring.auth.domain;

public record LoginResult(
	String accessToken,
	String refreshToken,
	boolean isNewUser
) {
}
