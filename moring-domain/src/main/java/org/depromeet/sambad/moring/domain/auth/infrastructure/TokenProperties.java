package org.depromeet.sambad.moring.domain.auth.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "jwt")
public record TokenProperties(
	@NotNull String secretKey,
	@NestedConfigurationProperty
	@NotNull ExpirationTime expirationTime
) {

	public record ExpirationTime(
		@Min(0) long accessToken,
		@Min(0) long refreshToken
	) {
	}
}
