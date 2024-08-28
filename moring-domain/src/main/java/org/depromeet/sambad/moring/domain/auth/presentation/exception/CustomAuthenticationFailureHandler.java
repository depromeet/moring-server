package org.depromeet.sambad.moring.domain.auth.presentation.exception;

import java.io.IOException;

import org.depromeet.sambad.moring.domain.auth.infrastructure.SecurityProperties;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private final SecurityProperties securityProperties;

	@Override
	public void onAuthenticationFailure(
		HttpServletRequest request, HttpServletResponse response, AuthenticationException exception
	) throws IOException, ServletException {
		// 에러 메시지를 쿼리 파라미터로 추가하여 리다이렉트
		super.setDefaultFailureUrl(securityProperties.loginUrl() + "?error=true&exception=" + AuthExceptionCode.AUTHENTICATION_REQUIRED);
		super.onAuthenticationFailure(request, response, exception);
	}
}

