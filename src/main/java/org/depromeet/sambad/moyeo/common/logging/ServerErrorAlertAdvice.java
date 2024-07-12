package org.depromeet.sambad.moyeo.common.logging;

import static org.springframework.http.HttpStatus.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.depromeet.sambad.moyeo.common.exception.BusinessException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;

@Profile({"dev", "prod"})
@Slf4j
@Aspect
@Component
public class ServerErrorAlertAdvice {

	@Pointcut("execution(* org.depromeet.sambad.moyeo..*(..))")
	private void allPointcut() {
	}

	@AfterThrowing(value = "allPointcut()", throwing = "exception")
	public void logAndAlertAfterThrowing(JoinPoint joinPoint, Throwable exception) {
		if (!(exception instanceof BusinessException)) {
			return;
		}

		BusinessException businessException = (BusinessException)exception;
		if (!businessException.getCode().getStatus().equals(INTERNAL_SERVER_ERROR)) {
			return;
		}

		MethodSignature signature = (MethodSignature)joinPoint.getSignature();

		String className = signature.getDeclaringType().getSimpleName();
		Method method = signature.getMethod();

		List<String> arguments = Arrays.stream(joinPoint.getArgs())
			.map(Object::toString)
			.collect(Collectors.toList());
		String parameterLog = IntStream.range(0, signature.getParameterNames().length)
			.mapToObj(i -> signature.getParameterNames()[i] + " = " + arguments.get(i))
			.collect(Collectors.joining(" | "));

		Sentry.captureException(exception);
		log.error("[SERVER ERROR] {} | {} | throwing = {} | reqArgs : {}", className, method.getName(),
			businessException.getCode().getCode(), parameterLog);
		log.error("[SERVER ERROR DESCRIPTION] code : {} | message : {}", businessException.getCode(),
			businessException.getMessage());
	}
}
