package org.depromeet.sambad.moring.user.domain;

import java.util.Optional;

public interface UserRepository {

	User save(User user);

	Optional<User> findByEmail(String email);

	Optional<User> findById(Long userId);
}
