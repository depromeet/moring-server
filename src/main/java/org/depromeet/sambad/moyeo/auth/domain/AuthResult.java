package org.depromeet.sambad.moyeo.auth.domain;

public record AuthResult(
		String accessToken,
		boolean isNewUser
) {
}
