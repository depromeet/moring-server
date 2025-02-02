package org.depromeet.sambad.moring.domain.auth.application;

import org.depromeet.sambad.moring.domain.auth.domain.CustomUserDetails;
import org.depromeet.sambad.moring.domain.user.domain.User;
import org.depromeet.sambad.moring.domain.user.domain.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2UserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Long userId = Long.valueOf(username);
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

		return new CustomUserDetails(user.getId());
	}
}
