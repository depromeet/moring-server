package org.depromeet.sambad.moring.auth.domain;

public interface TokenGenerator {

	String generateAccessToken(Long userId);

	String generateRefreshToken(Long userId);
}
