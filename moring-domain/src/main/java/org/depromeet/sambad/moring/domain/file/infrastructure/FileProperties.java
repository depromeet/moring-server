package org.depromeet.sambad.moring.domain.file.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public record FileProperties(
	String uploadPath,
	String baseUrl
) {
}
