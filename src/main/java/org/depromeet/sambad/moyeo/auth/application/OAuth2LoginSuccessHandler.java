package org.depromeet.sambad.moyeo.auth.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moyeo.auth.domain.CustomOAuth2User;
import org.depromeet.sambad.moyeo.auth.domain.LoginResult;
import org.depromeet.sambad.moyeo.auth.infrastructure.TokenProperties;
import org.depromeet.sambad.moyeo.auth.presentation.exception.AlreadyRegisteredUserException;
import org.depromeet.sambad.moyeo.auth.presentation.exception.AuthExceptionCode;
import org.depromeet.sambad.moyeo.common.exception.ExceptionCode;
import org.depromeet.sambad.moyeo.common.exception.ExceptionResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;
import static org.depromeet.sambad.moyeo.auth.presentation.exception.AuthExceptionCode.ALREADY_REGISTERED_USER;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.ACCESS_TOKEN;

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final AuthService authService;
	private final TokenProperties tokenProperties;
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request, HttpServletResponse response, Authentication authentication
	) throws IOException {
		try {
			LoginResult result = resolveLoginResultFromAuthentication(authentication);
			injectTokenToCookie(result, response);
			handleNewUserResponse(result, response);
		} catch (AlreadyRegisteredUserException e) {
			handleAlreadyExistUser(response);
		}
	}

	private LoginResult resolveLoginResultFromAuthentication(Authentication authentication) {
		CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
		return authService.handleLoginSuccess(oAuth2User.getAuthAttributes());
	}

	private void injectTokenToCookie(LoginResult result, HttpServletResponse response) {
		int expirationSeconds = (int) (tokenProperties.expirationTimeMs() / 1000);

		Cookie cookie = new Cookie(ACCESS_TOKEN, result.accessToken());
		cookie.setPath("/");
		cookie.setMaxAge(expirationSeconds);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

	private void handleNewUserResponse(LoginResult result, HttpServletResponse response) throws IOException {
		if (result.isNewUser()) {
			response.setStatus(SC_CREATED);
			response.getWriter().flush();  // Ensuring the response is flushed with the new status
		}
	}

	private void handleAlreadyExistUser(HttpServletResponse response) throws IOException {
		response.setStatus(SC_BAD_REQUEST);
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		objectMapper.writeValue(response.getWriter(), ExceptionResponse.from(ALREADY_REGISTERED_USER));
	}
}
