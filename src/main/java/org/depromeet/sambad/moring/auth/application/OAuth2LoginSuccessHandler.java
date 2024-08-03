package org.depromeet.sambad.moring.auth.application;

import static org.depromeet.sambad.moring.auth.presentation.RedirectUrlFilter.*;
import static org.depromeet.sambad.moring.auth.presentation.exception.AuthExceptionCode.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import org.depromeet.sambad.moring.auth.domain.CustomOAuth2User;
import org.depromeet.sambad.moring.auth.domain.LoginResult;
import org.depromeet.sambad.moring.auth.infrastructure.SecurityProperties;
import org.depromeet.sambad.moring.auth.presentation.exception.AlreadyRegisteredUserException;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final AuthService authService;
	private final TokenInjector tokenInjector;
	private final MeetingMemberService meetingMemberService;

	private final SecurityProperties securityProperties;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request, HttpServletResponse response, Authentication authentication
	) throws IOException {
		try {
			LoginResult result = resolveLoginResultFromAuthentication(authentication);
			tokenInjector.injectTokensToCookie(result, response);
			redirectToSuccessUrl(result, request, response);
		} catch (AlreadyRegisteredUserException e) {
			handleAlreadyExistUser(response);
		}
	}

	private LoginResult resolveLoginResultFromAuthentication(Authentication authentication) {
		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
		return authService.handleLoginSuccess(oAuth2User.getAuthAttributes());
	}

	private void redirectToSuccessUrl(
		LoginResult result, HttpServletRequest request, HttpServletResponse response
	) throws IOException {
		String redirectUrlByCookie = getRedirectUrlByCookie(request);
		String redirectUrl = determineRedirectUrl(result, redirectUrlByCookie);
		response.sendRedirect(redirectUrl);
		tokenInjector.invalidateCookie(REDIRECT_URL_COOKIE_NAME, response);
	}

	private String getRedirectUrlByCookie(HttpServletRequest request) {
		return Arrays.stream(request.getCookies())
			.filter(cookie -> Objects.equals(cookie.getName(), REDIRECT_URL_COOKIE_NAME))
			.findFirst()
			.map(Cookie::getValue)
			.orElse(null);
	}

	private String determineRedirectUrl(LoginResult result, String redirectCookie) {
		if (StringUtils.hasText(redirectCookie)) {
			return redirectCookie;
		}

		if (meetingMemberService.isNotEnterAnyMeeting(result.userId())) {
			return result.isNewUser()
				? securityProperties.newUserRedirectUrl() + "?newUser=true"
				: securityProperties.newUserRedirectUrl();
		}

		return securityProperties.redirectUrl();
	}

	private void handleAlreadyExistUser(HttpServletResponse response) throws IOException {
		response.sendRedirect(securityProperties.loginUrl() + "?error=true&exception=" + ALREADY_REGISTERED_USER);
	}
}
