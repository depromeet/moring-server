package org.depromeet.sambad.moyeo.auth.domain;

public record LoginResult(
		String accessToken,
		boolean isNewUser
) {
}
