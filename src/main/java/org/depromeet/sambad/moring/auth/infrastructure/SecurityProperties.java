package org.depromeet.sambad.moring.auth.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security")
public record SecurityProperties(
		String subDomain,
		String loginUrl,
		String redirectUrl
) {
}
