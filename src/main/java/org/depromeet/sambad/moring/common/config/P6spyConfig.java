package org.depromeet.sambad.moring.common.config;

import org.depromeet.sambad.moring.common.logging.P6spySqlLoggingFormatter;
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
