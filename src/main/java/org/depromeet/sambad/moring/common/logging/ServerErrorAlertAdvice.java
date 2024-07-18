package org.depromeet.sambad.moring.common.logging;

import static org.springframework.http.HttpStatus.*;

import java.lang.reflect.Method;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.depromeet.sambad.moring.common.exception.BusinessException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;

@Profile({"dev", "prod"})
@Slf4j
@Aspect
@Component
public class ServerErrorAlertAdvice {

	@Pointcut(
		"within(org.depromeet.sambad.moring..*) && " + "!@within(org.depromeet.sambad.moring.auth.presentation.*Filter)"
	)
	private void allPointcut() {
	}

	@AfterThrowing(value = "allPointcut()", throwing = "exception")
	public void logAndAlertAfterThrowing(JoinPoint joinPoint, Throwable exception) {
		if (!(exception instanceof BusinessException)) {
			return;
		}

		BusinessException businessException = (BusinessException)exception;
		if (isNotServerError(businessException)) {
			return;
		}

		Sentry.captureException(exception);

		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		Method method = signature.getMethod();

		String[] parameterNames = signature.getParameterNames();
		List<String> arguments = LoggingUtils.getArguments(joinPoint);
		String parameterMessage = LoggingUtils.getParameterMessage(parameterNames, arguments);

		log.error("[SERVER ERROR] {} | {} | throwing = {} | reqArgs : {}", className, method.getName(),
			businessException.getCode().getCode(), parameterMessage);
		log.error("[SERVER ERROR DESCRIPTION] code : {} | message : {}", businessException.getCode(),
			businessException.getMessage());
		log.error(exception.getCause().toString());
	}

	private boolean isNotServerError(BusinessException exception) {
		return !exception.getCode().getStatus().equals(INTERNAL_SERVER_ERROR);
	}
}