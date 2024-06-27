package org.depromeet.sambad.moyeo.user.infrastructure;

import org.depromeet.sambad.moyeo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
