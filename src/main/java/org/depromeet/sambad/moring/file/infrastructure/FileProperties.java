package org.depromeet.sambad.moring.file.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public record FileProperties(
	String uploadPath
) {
}
