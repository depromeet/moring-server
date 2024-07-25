package org.depromeet.sambad.moring.auth.infrastructure;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.crypto.SecretKey;

import org.depromeet.sambad.moring.auth.domain.TokenResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenResolver implements TokenResolver {

	private static final String REPLACE_BEARER_PATTERN = "^Bearer( )*";
	private static final Pattern BEARER_PATTERN = Pattern.compile("^Bearer .*");

	private final SecretKey secretKey;

	public JwtTokenResolver(TokenProperties tokenProperties) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenProperties.secretKey()));
	}

	@Override
	public Optional<String> resolveTokenFromRequest(HttpServletRequest request) {
		return resolveFromHeader(request)
			.or(() -> resolveFromCookie(request));
	}

	@Override
	public String getSubjectFromToken(String token) {
		return getClaims(token, secretKey)
			.getPayload()
			.getSubject();
	}

	private Optional<String> resolveFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (Objects.isNull(cookies)) {
			return Optional.empty();
		}

		return Arrays.stream(cookies)
			.filter(cookie -> Objects.equals(cookie.getName(), ACCESS_TOKEN))
			.map(Cookie::getValue)
			.findFirst();
	}

	private static Optional<String> resolveFromHeader(HttpServletRequest request) {
		Iterator<String> authorizations = request.getHeaders(AUTHORIZATION).asIterator();

		return Optional.ofNullable(authorizations)
			.filter(Iterator::hasNext)
			.map(Iterator::next)
			.filter(auth -> StringUtils.hasText(auth) && BEARER_PATTERN.matcher(auth).matches())
			.map(auth -> auth.replaceAll(REPLACE_BEARER_PATTERN, ""));
	}

	private Jws<Claims> getClaims(String token, SecretKey secretKey) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token);
	}
}
