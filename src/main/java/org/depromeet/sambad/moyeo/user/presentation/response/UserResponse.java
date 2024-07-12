package org.depromeet.sambad.moyeo.user.presentation.response;

import org.depromeet.sambad.moyeo.user.domain.User;

import java.time.LocalDateTime;

public record UserResponse(
		String name,
		String email,
		String profileImageUrl,
		LocalDateTime createAt,
		LocalDateTime updateAt
) {

	public static UserResponse from(User user) {
		return new UserResponse(
				user.getName(),
				user.getEmail(),
				user.getProfileImageUrl(),
				user.getCreateAt(),
				user.getUpdateAt()
		);
	}
}
