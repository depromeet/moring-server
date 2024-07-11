package org.depromeet.sambad.moyeo.user.application;

import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moyeo.user.domain.UserRepository;
import org.depromeet.sambad.moyeo.user.presentation.exception.NotFoundUserException;
import org.depromeet.sambad.moyeo.user.presentation.response.UserResponse;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponse findByUserId(Long userId) {
        return userRepository.findById(userId)
            .map(UserResponse::from)
            .orElseThrow(NotFoundUserException::new);
    }
}
