package org.depromeet.sambad.moyeo.common.logging;

import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingUtils {

	public static void error(Exception exception) {
		String message = getExceptionMessage(exception.getMessage());
		StackTraceElement[] stackTraceElements = exception.getStackTrace();

		log.error("[SERVER ERROR] {} {}", message, stackTraceElements[0]);
	}

	private static String getExceptionMessage(String message) {
		if (StringUtils.hasText(message)) {
			return message + "\n \t";
		}
		return "\n \t";
	}
}
