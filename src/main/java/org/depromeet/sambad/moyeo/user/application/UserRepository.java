package org.depromeet.sambad.moyeo.user.application;

import java.util.Optional;

import org.depromeet.sambad.moyeo.user.domain.User;

public interface UserRepository {

	void save(User user);

	Optional<User> findByEmail(String email);
}
