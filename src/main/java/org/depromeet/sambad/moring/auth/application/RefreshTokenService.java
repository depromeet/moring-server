package org.depromeet.sambad.moring.auth.application;

import org.depromeet.sambad.moring.auth.domain.LoginResult;
import org.depromeet.sambad.moring.auth.domain.RefreshToken;
import org.depromeet.sambad.moring.auth.domain.TokenGenerator;
import org.depromeet.sambad.moring.auth.domain.TokenResolver;
import org.depromeet.sambad.moring.auth.infrastructure.TokenProperties;
import org.depromeet.sambad.moring.auth.presentation.exception.AuthenticationRequiredException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;
	private final TokenGenerator tokenGenerator;
	private final TokenResolver tokenResolver;
	private final TokenInjector tokenInjector;
	private final TokenProperties tokenProperties;

	@Transactional
	public LoginResult reissueBasedOnRefreshToken(HttpServletRequest request, HttpServletResponse response) {
		String refreshToken = tokenResolver.resolveRefreshTokenFromRequest(request)
			.orElseThrow(AuthenticationRequiredException::new);

		RefreshToken savedRefreshToken = validateAndGetSavedRefreshToken(refreshToken);

		return getReissuedTokenResult(response, savedRefreshToken);
	}

	private LoginResult getReissuedTokenResult(HttpServletResponse response, RefreshToken savedRefreshToken) {
		Long userId = savedRefreshToken.getUserId();

		String reissuedAccessToken = tokenGenerator.generateAccessToken(userId);
		String rotatedRefreshToken = this.rotate(savedRefreshToken);

		LoginResult loginResult = new LoginResult(
			reissuedAccessToken, rotatedRefreshToken, false, false, userId);
		tokenInjector.injectTokensToCookie(loginResult, response);

		return loginResult;
	}

	private RefreshToken validateAndGetSavedRefreshToken(String refreshToken) {
		Long userId = Long.valueOf(tokenResolver.getSubjectFromToken(refreshToken));
		RefreshToken savedRefreshToken = this.getByTokenString(refreshToken);

		savedRefreshToken.validateWith(refreshToken, userId);

		return savedRefreshToken;
	}

	public RefreshToken getByTokenString(String token) {
		return refreshTokenRepository.findByToken(token)
			.orElseThrow(AuthenticationRequiredException::new);
	}

	private String rotate(RefreshToken refreshToken) {
		String reissuedToken = tokenGenerator.generateRefreshToken(refreshToken.getUserId());
		refreshToken.rotate(reissuedToken);
		refreshToken.updateExpirationIfExpired(tokenProperties.expirationTime().refreshToken());
		refreshTokenRepository.save(refreshToken);

		return reissuedToken;
	}
}

