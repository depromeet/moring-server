package org.depromeet.sambad.moring.infra.config;

import javax.sql.DataSource;

import org.depromeet.sambad.moring.infra.MoringConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;

@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class SchedulingConfig implements MoringConfig {

	@Bean
	public LockProvider lockProvider(DataSource dataSource) {
		return new JdbcTemplateLockProvider(dataSource);
	}
}
