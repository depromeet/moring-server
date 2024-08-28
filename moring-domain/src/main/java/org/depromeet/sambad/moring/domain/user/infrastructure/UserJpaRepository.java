package org.depromeet.sambad.moring.domain.user.infrastructure;

import java.util.Optional;

import org.depromeet.sambad.moring.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
