package org.depromeet.sambad.moring.auth.application;

import java.util.Optional;

import org.depromeet.sambad.moring.auth.domain.RefreshToken;

public interface RefreshTokenRepository {

	Optional<RefreshToken> findByToken(String token);

	void save(RefreshToken refreshToken);

	Optional<RefreshToken> findByUserId(Long id);
}
