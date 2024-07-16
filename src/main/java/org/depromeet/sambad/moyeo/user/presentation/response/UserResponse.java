package org.depromeet.sambad.moyeo.user.presentation.response;

import org.depromeet.sambad.moyeo.user.domain.User;

import java.time.LocalDateTime;

/**
 * TODO: 로그인 성공 여부 확인 용으로 임시로 만들었으며, 제공할 정보 및 API 스펙이 확정되면 수정이 필요합니다.
 */
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
