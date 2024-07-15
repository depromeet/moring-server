package org.depromeet.sambad.moyeo.auth.presentation.exception;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moyeo.auth.infrastructure.SecurityProperties;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.depromeet.sambad.moyeo.auth.presentation.exception.AuthExceptionCode.AUTHENTICATION_REQUIRED;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private final SecurityProperties securityProperties;

	@Override
	public void onAuthenticationFailure(
			HttpServletRequest request, HttpServletResponse response, AuthenticationException exception
	) throws IOException, ServletException {
		// 에러 메시지를 쿼리 파라미터로 추가하여 리다이렉트
		super.setDefaultFailureUrl(securityProperties.loginUrl() + "?error=true&exception=" + AUTHENTICATION_REQUIRED);
		super.onAuthenticationFailure(request, response, exception);
	}
}

