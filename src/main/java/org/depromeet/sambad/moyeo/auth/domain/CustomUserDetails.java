package org.depromeet.sambad.moyeo.auth.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

	private final Long userId;

	public CustomUserDetails(Long userId) {
		this.userId = userId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getPassword() {
		return "";
	}

	@Override
	public String getUsername() {
		return this.userId.toString();
	}
}
