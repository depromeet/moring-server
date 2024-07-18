package org.depromeet.sambad.moring.auth.domain;

public record LoginResult(
		String accessToken,
		boolean isNewUser
) {
}
