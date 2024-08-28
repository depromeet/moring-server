package org.depromeet.sambad.moring.infra.config;

import org.depromeet.sambad.moring.globalutils.logging.P6spySqlLoggingFormatter;
import org.depromeet.sambad.moring.infra.MoringConfig;
import org.springframework.context.annotation.Profile;

import com.p6spy.engine.logging.P6LogLoadableOptions;
import com.p6spy.engine.logging.P6LogOptions;
import com.p6spy.engine.spy.P6SpyLoadableOptions;
import com.p6spy.engine.spy.P6SpyOptions;

import jakarta.annotation.PostConstruct;

@Profile({"local", "dev"})
public class P6spyConfig implements MoringConfig {

	@PostConstruct
	public void setLogMessageFormat() {
		P6SpyLoadableOptions spyActiveInstance = P6SpyOptions.getActiveInstance();
		P6LogLoadableOptions logActiveInstance = P6LogOptions.getActiveInstance();

		if (spyActiveInstance == null || logActiveInstance == null) {
			return;
		}

		spyActiveInstance.setLogMessageFormat(P6spySqlLoggingFormatter.class.getName());

		// exclude sql pattern
		logActiveInstance.setFilter(true);
		logActiveInstance.setExclude("shedlock");
	}
}