package org.depromeet.sambad.moyeo.user.application;

import org.depromeet.sambad.moyeo.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findByEmail(String email);
}
