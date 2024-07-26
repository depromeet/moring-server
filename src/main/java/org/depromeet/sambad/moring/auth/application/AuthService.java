package org.depromeet.sambad.moring.auth.application;

import org.depromeet.sambad.moring.auth.application.dto.AuthAttributes;
import org.depromeet.sambad.moring.auth.domain.LoginResult;
import org.depromeet.sambad.moring.auth.domain.TokenGenerator;
import org.depromeet.sambad.moring.auth.presentation.exception.AlreadyRegisteredUserException;
import org.depromeet.sambad.moring.file.application.FileService;
import org.depromeet.sambad.moring.file.domain.FileEntity;
import org.depromeet.sambad.moring.user.domain.User;
import org.depromeet.sambad.moring.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

		return new LoginResult(generateTokenFromUser(user), false);
	}

	private LoginResult handleFirstLogin(AuthAttributes attributes) {
		User newUser = saveNewUser(attributes);

		return new LoginResult(generateTokenFromUser(newUser), true);
	}

	private String generateTokenFromUser(User user) {
		return tokenGenerator.generate(user.getId());
	}

	private User saveNewUser(AuthAttributes attributes) {
		FileEntity file = uploadProfileImage(attributes);
		User user = User.from(file, attributes);

		return userRepository.save(user);
	}

	private FileEntity uploadProfileImage(AuthAttributes attributes) {
		String profileImageUrl = attributes.getProfileImageUrl();

		return StringUtils.hasText(profileImageUrl)
			? fileService.uploadAndSave(profileImageUrl)
			: fileService.getRandomProfileImage();
	}
}
