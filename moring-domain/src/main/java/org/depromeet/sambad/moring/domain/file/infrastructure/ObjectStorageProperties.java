package org.depromeet.sambad.moring.domain.file.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

@Validated
@ConfigurationProperties(prefix = "cloud.ncp")
public record ObjectStorageProperties(
	@NotNull ObjectStorage objectStorage,
	@NotNull Region region
) {

	public record ObjectStorage(
		@NotNull String endpoint,
		@NotNull Credentials credentials) {
	}

	public record Credentials(
		@NotNull String accessKey,
		@NotNull String secretKey,
		@NotNull String bucket) {
	}

	public record Region(
		@NotNull String staticRegion,
		boolean auto) {
	}
}
