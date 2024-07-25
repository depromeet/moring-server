package org.depromeet.sambad.moring.user.infrastructure;

import java.util.Optional;

import org.depromeet.sambad.moring.user.domain.User;
import org.depromeet.sambad.moring.user.domain.UserRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository userJpaRepository;

	@Override
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
