package org.depromeet.sambad.moring.auth.domain;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenResolver {

	Optional<String> resolveTokenFromRequest(HttpServletRequest request);

	Optional<String> resolveRefreshTokenFromRequest(HttpServletRequest request);

	String getSubjectFromToken(String token);
}
