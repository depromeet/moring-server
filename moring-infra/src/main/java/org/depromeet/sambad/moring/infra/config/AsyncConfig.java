package org.depromeet.sambad.moring.infra.config;

import java.util.concurrent.Executor;

import org.depromeet.sambad.moring.globalutils.logging.LoggingUtils;
import org.depromeet.sambad.moring.infra.MoringConfig;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import io.sentry.Sentry;

@EnableAsync
public class AsyncConfig implements AsyncConfigurer, MoringConfig {

	@Value("${spring.task.execution.pool.core-size}")
	private int CORE_POOL_SIZE;
	@Value("${spring.task.execution.pool.max-size}")
	private int MAX_POOL_SIZE;
	@Value("${spring.task.execution.pool.queue-capacity}")
	private int QUEUE_CAPACITY;
	@Value("${spring.task.execution.pool.keep-alive}")
	private int KEEP_ALIVE_SECONDS;
	@Value("${spring.task.execution.thread-name-prefix}")
	private String THREAD_NAME_PREFIX;

	@Override
	@Bean(name = "asyncExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.initialize();
		threadPoolTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
		threadPoolTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
		threadPoolTaskExecutor.setQueueCapacity(QUEUE_CAPACITY);
		threadPoolTaskExecutor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
		threadPoolTaskExecutor.setThreadNamePrefix(THREAD_NAME_PREFIX);
		return threadPoolTaskExecutor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (ex, method, params) -> {
			Sentry.captureException(ex);
			LoggingUtils.error(
				"[Async Uncaught Exception] Method name : " + method.getName() + ", param Count : " + params.length
					+ "\n\nException Cause -" + ex.getMessage());
			LoggingUtils.error((Exception)ex);
		};
	}
}