package org.depromeet.sambad.moring.user.presentation.response;

import java.time.LocalDateTime;

import org.depromeet.sambad.moring.user.domain.User;

/**
 * TODO: 로그인 성공 여부 확인 용으로 임시로 만들었으며, 제공할 정보 및 API 스펙이 확정되면 수정이 필요합니다.
 */
public record UserResponse(
	String name,
	String email,
	String profileImageFileUrl,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {

	public static UserResponse from(User user) {
		return new UserResponse(
			user.getName(),
			user.getEmail(),
			user.getProfileImageFileUrl(),
			user.getCreatedAt(),
			user.getUpdatedAt()
		);
	}
}
