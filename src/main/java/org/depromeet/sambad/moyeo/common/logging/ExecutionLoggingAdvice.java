package org.depromeet.sambad.moyeo.common.logging;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.depromeet.sambad.moyeo.common.exception.BusinessException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Profile("local")
@Slf4j
@Aspect
@Component
public class ExecutionLoggingAdvice {

	@Pointcut("execution(* org.depromeet.sambad.moyeo..*(..))")
	private void allPointcut() {
	}

	@Pointcut("@annotation(org.depromeet.sambad.moyeo.common.logging.ExecutionTimer)")
	private void executionTimer() {
	}

	@Around("executionTimer()")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();
		Object proceed = joinPoint.proceed();
		stopWatch.stop();

		long totalTimeMillis = stopWatch.getTotalTimeMillis();

		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		String methodName = signature.getMethod().getName();

		log.info("실행 메서드: {}, 실행시간 = {}ms", methodName, totalTimeMillis);
		return proceed;
	}

	@Before("allPointcut()")
	public void logBeforeExecution(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Class className = signature.getDeclaringType();
		Method method = signature.getMethod();

		List<String> arguments = Arrays.stream(joinPoint.getArgs())
			.map(Object::toString)
			.collect(Collectors.toList());
		String parameterLog = IntStream.range(0, signature.getParameterNames().length)
			.mapToObj(i -> signature.getParameterNames()[i] + " = " + arguments.get(i))
			.collect(Collectors.joining(" | "));

		log.info("[START] {} | {} {}", className.getSimpleName(), method.getName(), parameterLog);
	}

	@AfterReturning(value = "allPointcut()", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Class className = signature.getDeclaringType();
		Method method = signature.getMethod();

		log.info("[SUCCESS] {} | {} | return = {}", className.getSimpleName(), method.getName(), result);
	}

	@AfterThrowing(value = "allPointcut()", throwing = "exception")
	public void logAfterThrowing(JoinPoint joinPoint, BusinessException exception) {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();

		String className = signature.getDeclaringType().getSimpleName();
		Method method = signature.getMethod();

		List<String> arguments = Arrays.stream(joinPoint.getArgs())
			.map(Object::toString)
			.collect(Collectors.toList());
		String parameterLog = IntStream.range(0, signature.getParameterNames().length)
			.mapToObj(i -> signature.getParameterNames()[i] + " = " + arguments.get(i))
			.collect(Collectors.joining(" | "));

		log.error("[ERROR] {} | {} | throwing = {} | reqArgs : {}", className, method.getName(),
			exception.getCode().getCode(), parameterLog);
	}
}