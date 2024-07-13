package org.depromeet.sambad.moyeo.auth.domain;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface TokenResolver {

	Optional<String> resolveTokenFromRequest(HttpServletRequest request);

	String getSubjectFromToken(String token);
}
