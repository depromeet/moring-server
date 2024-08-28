package org.depromeet.sambad.moring.domain.auth.infrastructure;

import java.util.Optional;

import org.depromeet.sambad.moring.domain.auth.application.RefreshTokenRepository;
import org.depromeet.sambad.moring.domain.auth.domain.RefreshToken;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

	private final RefreshTokenJpaRepository refreshTokenJpaRepository;

	@Override
	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenJpaRepository.findByToken(token);
	}

	@Override
	public Optional<RefreshToken> findByUserId(Long id) {
		return refreshTokenJpaRepository.findFirstByUserIdOrderByIdDesc(id);
	}

	@Override
	public void save(RefreshToken refreshToken) {
		refreshTokenJpaRepository.save(refreshToken);
	}
}
