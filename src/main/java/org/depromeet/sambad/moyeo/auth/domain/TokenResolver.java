package org.depromeet.sambad.moyeo.auth.domain;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface TokenResolver {

    Optional<String> resolveTokenFromRequest(HttpServletRequest request);

    boolean isTokenExpired(String token);

    String getSubjectFromToken(String token);
}
