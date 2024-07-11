package org.depromeet.sambad.moyeo.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moyeo.user.domain.UserRepository;
import org.depromeet.sambad.moyeo.user.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId);
    }
}
