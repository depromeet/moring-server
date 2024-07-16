package org.depromeet.sambad.moyeo.auth.application;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moyeo.auth.domain.CustomOAuth2User;
import org.depromeet.sambad.moyeo.auth.domain.LoginResult;
import org.depromeet.sambad.moyeo.auth.infrastructure.SecurityProperties;
import org.depromeet.sambad.moyeo.auth.infrastructure.TokenProperties;
import org.depromeet.sambad.moyeo.auth.presentation.exception.AlreadyRegisteredUserException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.depromeet.sambad.moyeo.auth.presentation.exception.AuthExceptionCode.ALREADY_REGISTERED_USER;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.ACCESS_TOKEN;

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final AuthService authService;
	private final TokenProperties tokenProperties;
	private final SecurityProperties securityProperties;

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request, HttpServletResponse response, Authentication authentication
	) throws IOException {
		try {
			LoginResult result = resolveLoginResultFromAuthentication(authentication);
			injectTokenToCookie(result, response);
			redirectToSuccessUrl(result, response);
		} catch (AlreadyRegisteredUserException e) {
			handleAlreadyExistUser(response);
		}
	}

	private LoginResult resolveLoginResultFromAuthentication(Authentication authentication) {
		CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
		return authService.handleLoginSuccess(oAuth2User.getAuthAttributes());
	}

	private void injectTokenToCookie(LoginResult result, HttpServletResponse response) throws IOException {
		int expirationSeconds = (int) (tokenProperties.expirationTimeMs() / 1000);

		Cookie cookie = new Cookie(ACCESS_TOKEN, result.accessToken());
		cookie.setPath("/");
		cookie.setMaxAge(expirationSeconds);
		cookie.setHttpOnly(true);
		cookie.setDomain(securityProperties.subDomain());

		response.addCookie(cookie);
	}

	private void redirectToSuccessUrl(LoginResult result, HttpServletResponse response) throws IOException {
		String redirectUrl = determineRedirectUrl(result);
		response.sendRedirect(redirectUrl);
	}

	private String determineRedirectUrl(LoginResult result) {
		String redirectUrl = securityProperties.redirectUrl();
		if (result.isNewUser()) {
			redirectUrl += "?newUser=true";
		}
		return redirectUrl;
	}


	private void handleAlreadyExistUser(HttpServletResponse response) throws IOException {
		response.sendRedirect(securityProperties.loginUrl() + "?error=true&exception=" + ALREADY_REGISTERED_USER);
	}
}
