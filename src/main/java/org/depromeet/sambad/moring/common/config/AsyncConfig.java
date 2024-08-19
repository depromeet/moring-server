package org.depromeet.sambad.moring.common.config;

import java.util.concurrent.Executor;

import org.depromeet.sambad.moring.common.logging.LoggingUtils;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import io.sentry.Sentry;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

	private final int CORE_POOL_SIZE = 10;
	private final int MAX_POOL_SIZE = 50;
	private final int QUEUE_CAPACITY = 100;
	private final int KEEP_ALIVE_SECONDS = 60;

	@Override
	@Bean(name = "asyncExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.initialize();
		threadPoolTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
		threadPoolTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
		threadPoolTaskExecutor.setQueueCapacity(QUEUE_CAPACITY);
		threadPoolTaskExecutor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
		return threadPoolTaskExecutor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (ex, method, params) -> {
			Sentry.captureException(ex);
			LoggingUtils.error(
				"[Async Uncaught Exception] Method name : " + method.getName() + ", param Count : " + params.length
					+ "\n\nException Cause -" + ex.getMessage());
		};
	}
}