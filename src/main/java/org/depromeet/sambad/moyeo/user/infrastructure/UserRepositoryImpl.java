package org.depromeet.sambad.moyeo.user.infrastructure;

import java.util.Optional;

import org.depromeet.sambad.moyeo.user.domain.User;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl {

	private final UserJpaRepository userJpaRepository;

	public User save(User user) {
		return userJpaRepository.save(user);
	}

	public Optional<User> findById(Long id) {
		return userJpaRepository.findById(id);
	}
}
