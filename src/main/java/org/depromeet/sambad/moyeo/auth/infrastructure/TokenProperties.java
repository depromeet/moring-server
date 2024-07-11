package org.depromeet.sambad.moyeo.auth.infrastructure;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record TokenProperties(
        @NotNull String secretKey,
        @Min(0) long expirationTimeMs
) {
}
