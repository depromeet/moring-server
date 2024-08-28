package org.depromeet.sambad.moring.infra.config;

import java.time.Clock;

import org.depromeet.sambad.moring.infra.MoringConfig;
import org.springframework.context.annotation.Bean;

public class TimeConfig implements MoringConfig {

	@Bean
	public Clock clock() {
		return Clock.systemDefaultZone();
	}
}
