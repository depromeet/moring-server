package org.depromeet.sambad.moyeo.user.domain;

import java.util.Optional;

public interface UserRepository {

	User save(User user);

	Optional<User> findByEmail(String email);

	Optional<User> findById(Long userId);
}
