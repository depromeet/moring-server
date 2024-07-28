package org.depromeet.sambad.moring.common.logging;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.aspectj.lang.JoinPoint;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingUtils {

	public static void error(Exception exception) {
		String message = getExceptionMessage(exception.getMessage());
		StackTraceElement[] stackTraceElements = exception.getStackTrace();

		log.error("[SERVER ERROR] {} {}", message, stackTraceElements[0], exception);
	}

	public static void error(String message) {
		log.error("[SERVER ERROR] {}", message);
	}

	static List<String> getArguments(JoinPoint joinPoint) {
		return Arrays.stream(joinPoint.getArgs())
			.map(Object::toString)
			.toList();
	}

	static String getParameterMessage(String[] parameterNames, List<String> arguments) {
		if (parameterNames == null || arguments == null)
			return "";

		return IntStream.range(0, parameterNames.length)
			.mapToObj(i -> parameterNames[i] + " = " + arguments.get(i))
			.collect(Collectors.joining(" | "));
	}

	private static String getExceptionMessage(String message) {
		if (StringUtils.hasText(message)) {
			return message + "\n \t";
		}
		return "\n \t";
	}
}
