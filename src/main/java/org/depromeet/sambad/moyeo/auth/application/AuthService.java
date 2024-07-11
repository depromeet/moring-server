package org.depromeet.sambad.moyeo.auth.application;

import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moyeo.auth.application.dto.AuthAttributes;
import org.depromeet.sambad.moyeo.auth.domain.AuthResult;
import org.depromeet.sambad.moyeo.auth.domain.TokenGenerator;
import org.depromeet.sambad.moyeo.file.application.FileService;
import org.depromeet.sambad.moyeo.file.domain.FileEntity;
import org.depromeet.sambad.moyeo.user.domain.UserRepository;
import org.depromeet.sambad.moyeo.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final FileService fileService;

    @Transactional
    public AuthResult handleLoginSuccess(AuthAttributes attributes) {
        String email = attributes.getEmail();

        return userRepository.findByEmail(email)
                .map(this::handleExistUser)
                .orElseGet(() -> handleFirstLogin(attributes));
    }

    private AuthResult handleExistUser(User user) {
        return new AuthResult(generateTokenFromUser(user), false);
    }

    private AuthResult handleFirstLogin(AuthAttributes attributes) {
        User newUser = saveNewUser(attributes);

        return new AuthResult(generateTokenFromUser(newUser), true);
    }

    private String generateTokenFromUser(User user) {
        return tokenGenerator.generate(user.getId());
    }

    private User saveNewUser(AuthAttributes attributes) {
        FileEntity file = uploadProfileImage(attributes);

        return userRepository.save(
                User.of(file, attributes.getName(), attributes.getEmail(), attributes.getProvider())
        );
    }

    private FileEntity uploadProfileImage(AuthAttributes attributes) {
        String profileImageUrl = attributes.getProfileImageUrl();
        return fileService.uploadAndSave(profileImageUrl);
    }
}
