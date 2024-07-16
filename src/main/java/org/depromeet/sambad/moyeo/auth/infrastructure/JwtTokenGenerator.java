package org.depromeet.sambad.moyeo.auth.infrastructure;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moyeo.auth.domain.TokenGenerator;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.io.Decoders.BASE64;

@RequiredArgsConstructor
@Component
public class JwtTokenGenerator implements TokenGenerator {

	private final TokenProperties tokenProperties;

	public String generate(Long userId) {
		long currentTimeMillis = System.currentTimeMillis();
		Date now = new Date(currentTimeMillis);
		Date expiration = new Date(currentTimeMillis + tokenProperties.expirationTimeMs());
		SecretKey secretKey = Keys.hmacShaKeyFor(BASE64.decode(tokenProperties.secretKey()));

		return Jwts.builder()
				.subject(String.valueOf(userId))
				.issuedAt(now)
				.expiration(expiration)
				.signWith(secretKey)
				.compact();
	}
}
