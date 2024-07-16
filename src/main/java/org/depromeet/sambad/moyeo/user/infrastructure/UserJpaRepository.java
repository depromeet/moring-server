package org.depromeet.sambad.moyeo.user.infrastructure;

import org.depromeet.sambad.moyeo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
