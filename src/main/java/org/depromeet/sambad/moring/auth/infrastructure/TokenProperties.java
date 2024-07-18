package org.depromeet.sambad.moring.auth.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "jwt")
public record TokenProperties(
	@NotNull String secretKey,
	@Min(0) long expirationTimeMs
) {
}
