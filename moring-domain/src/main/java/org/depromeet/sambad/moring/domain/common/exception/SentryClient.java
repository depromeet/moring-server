package org.depromeet.sambad.moring.domain.common.exception;

import org.depromeet.sambad.moring.globalutils.logging.LoggingUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import io.sentry.Sentry;

@Component
public class SentryClient {

	@Async
	@EventListener
	public void notify(Exception exception) {
		Sentry.captureException(exception);
		LoggingUtils.error(exception);
	}
}
