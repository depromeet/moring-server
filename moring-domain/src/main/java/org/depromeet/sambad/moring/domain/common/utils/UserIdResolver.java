package org.depromeet.sambad.moring.domain.common.utils;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserIdResolver {

	public static Long resolveRequestedUserId() {
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
			.map(Authentication::getName)
			.map(Long::valueOf)
			.orElse(null);
	}
}
