package org.depromeet.sambad.moyeo.user.presentation.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasAnnotation = parameter.hasParameterAnnotation(UserId.class);
		boolean hasLongType = Long.class.isAssignableFrom(parameter.getParameterType());
		return hasAnnotation && hasLongType;
	}

	@Override
	public Object resolveArgument(
			MethodParameter parameter,
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory
	) {
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
				.map(Authentication::getName)
				.map(Long::valueOf)
				.orElse(null);
	}
}
