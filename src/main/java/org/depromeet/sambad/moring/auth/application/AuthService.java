package org.depromeet.sambad.moring.auth.application;

import org.depromeet.sambad.moring.auth.application.dto.AuthAttributes;
import org.depromeet.sambad.moring.auth.domain.LoginResult;
import org.depromeet.sambad.moring.auth.domain.RefreshToken;
import org.depromeet.sambad.moring.auth.domain.TokenGenerator;
import org.depromeet.sambad.moring.auth.infrastructure.TokenProperties;
import org.depromeet.sambad.moring.auth.presentation.exception.AlreadyRegisteredUserException;
import org.depromeet.sambad.moring.file.application.FileService;
import org.depromeet.sambad.moring.file.domain.FileEntity;
import org.depromeet.sambad.moring.user.domain.User;
import org.depromeet.sambad.moring.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 인증 정보에 기반한 로그인 성공 처리를 담당합니다.<br />
 * 로그인 성공 시, 토큰을 발급합니다.<br />
 * 최초 로그인일 경우, 사용자 정보를 신규 저장합니다.
 */
@RequiredArgsConstructor
@Service
public class AuthService {

	private final UserRepository userRepository;
	private final TokenGenerator tokenGenerator;
	private final FileService fileService;
	private final RefreshTokenRepository refreshTokenRepository;
	private final TokenProperties tokenProperties;

	@Transactional
	public LoginResult handleLoginSuccess(AuthAttributes attributes) {
		String email = attributes.getEmail();

		return userRepository.findByEmail(email)
			.map(user -> handleExistUser(user, attributes))
			.orElseGet(() -> handleFirstLogin(attributes));
	}

	private LoginResult handleExistUser(User user, AuthAttributes attributes) {
		if (user.hasDifferentProviderWithEmail(attributes.getEmail(), attributes.getExternalId())) {
			throw new AlreadyRegisteredUserException();
		}

		return generateLoginResult(user, false);
	}

	private LoginResult handleFirstLogin(AuthAttributes attributes) {
		User newUser = saveNewUser(attributes);

		return generateLoginResult(newUser, true);
	}

	private LoginResult generateLoginResult(User user, boolean firstLogin) {
		String accessToken = tokenGenerator.generateAccessToken(user.getId());
		String refreshToken = tokenGenerator.generateRefreshToken(user.getId());

		RefreshToken refreshTokenEntity = refreshTokenRepository.findByUserId(user.getId())
			.orElse(RefreshToken.of(user.getId(), refreshToken, tokenProperties.expirationTime().refreshToken()));

		refreshTokenEntity.rotate(refreshToken);
		refreshTokenRepository.save(refreshTokenEntity);

		return new LoginResult(accessToken, refreshToken, firstLogin, user.getId());
	}

	private User saveNewUser(AuthAttributes attributes) {
		FileEntity file = uploadProfileImage(attributes);
		User user = User.from(file, attributes);

		return userRepository.save(user);
	}

	private FileEntity uploadProfileImage(AuthAttributes attributes) {
		return attributes.hasDefaultProfileImage()
			? fileService.getRandomProfileImage()
			: fileService.uploadAndSave(attributes.getProfileImageUrl());
	}
}
