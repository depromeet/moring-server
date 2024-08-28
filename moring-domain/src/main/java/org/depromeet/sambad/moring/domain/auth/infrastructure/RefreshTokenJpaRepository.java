package org.depromeet.sambad.moring.domain.auth.infrastructure;

import java.util.Optional;

import org.depromeet.sambad.moring.domain.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByToken(String token);

	Optional<RefreshToken> findFirstByUserIdOrderByIdDesc(Long id);
}
