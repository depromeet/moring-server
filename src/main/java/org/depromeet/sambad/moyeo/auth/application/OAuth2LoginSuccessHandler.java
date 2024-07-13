package org.depromeet.sambad.moyeo.auth.application;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moyeo.auth.domain.CustomOAuth2User;
import org.depromeet.sambad.moyeo.auth.domain.LoginResult;
import org.depromeet.sambad.moyeo.auth.infrastructure.TokenProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.ACCESS_TOKEN;

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final AuthService authService;
	private final TokenProperties tokenProperties;

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request, HttpServletResponse response, Authentication authentication
	) throws IOException {
		LoginResult result = resolveLoginResultFromAuthentication(authentication);

		injectTokenToCookie(result, response);

		handleNewUserResponse(result, response);
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
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.getWriter().flush();  // Ensuring the response is flushed with the new status
		}
	}
}
