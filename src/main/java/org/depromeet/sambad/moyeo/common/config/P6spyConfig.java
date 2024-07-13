package org.depromeet.sambad.moyeo.common.config;

import org.depromeet.sambad.moyeo.common.logging.P6spySqlLoggingFormatter;
import org.springframework.context.annotation.Configuration;

import com.p6spy.engine.spy.P6SpyOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class P6spyConfig {

	@PostConstruct
	public void setLogMessageFormat() {
		P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spySqlLoggingFormatter.class.getName());
	}
}
