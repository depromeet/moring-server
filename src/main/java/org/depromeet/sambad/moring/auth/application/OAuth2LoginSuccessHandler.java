package org.depromeet.sambad.moring.auth.application;

import static org.depromeet.sambad.moring.auth.presentation.exception.AuthExceptionCode.*;

import java.io.IOException;

import org.depromeet.sambad.moring.auth.domain.CustomOAuth2User;
import org.depromeet.sambad.moring.auth.domain.LoginResult;
import org.depromeet.sambad.moring.auth.infrastructure.SecurityProperties;
import org.depromeet.sambad.moring.auth.presentation.exception.AlreadyRegisteredUserException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final AuthService authService;
	private final TokenInjector tokenInjector;

	private final SecurityProperties securityProperties;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request, HttpServletResponse response, Authentication authentication
	) throws IOException {
		try {
			LoginResult result = resolveLoginResultFromAuthentication(authentication);
			tokenInjector.injectTokensToCookie(result, response);
			redirectToSuccessUrl(result, response);
		} catch (AlreadyRegisteredUserException e) {
			handleAlreadyExistUser(response);
		}
	}

	private LoginResult resolveLoginResultFromAuthentication(Authentication authentication) {
		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
		return authService.handleLoginSuccess(oAuth2User.getAuthAttributes());
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
