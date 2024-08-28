package org.depromeet.sambad.moring.domain.common.logging;

import java.lang.reflect.Method;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.depromeet.sambad.moring.domain.common.exception.BusinessException;
import org.depromeet.sambad.moring.globalutils.logging.LoggingUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Profile({"dev", "prod"})
@Slf4j
@Aspect
@Component
public class ServerErrorAlertAdvice {

	@Pointcut("within(org.depromeet.sambad.moring..*) && "
		+ "!@within(org.depromeet.sambad.moring.domain.auth.presentation.*Filter)"
	)
	private void allPointcut() {
	}

	@AfterThrowing(value = "allPointcut()", throwing = "exception")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
		if (!(exception instanceof BusinessException)) {
			return;
		}

		BusinessException businessException = (BusinessException)exception;
		if (!businessException.isServerError()) {
			return;
		}

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
}