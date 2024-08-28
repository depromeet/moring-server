package org.depromeet.sambad.moring.domain.auth.domain;

public interface TokenGenerator {

	String generateAccessToken(Long userId);

	String generateRefreshToken(Long userId);
}
