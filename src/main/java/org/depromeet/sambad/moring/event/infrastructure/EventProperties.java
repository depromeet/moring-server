package org.depromeet.sambad.moring.event.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "event")
public record EventProperties(
	Long keepDays
) {
}
