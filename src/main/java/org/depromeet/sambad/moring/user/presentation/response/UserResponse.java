package org.depromeet.sambad.moring.user.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.time.LocalDateTime;

import org.depromeet.sambad.moring.user.domain.User;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponse(
	@Schema(description = "유저 이름", example = "홍길동", requiredMode = REQUIRED)
	String name,

	@Schema(description = "유저 이메일", example = "example@abc.com", requiredMode = REQUIRED)
	String email,

	@Schema(description = "프로필 이미지 경로", example = "https://example.com/profile.jpg", requiredMode = REQUIRED)
	String profileImageFileUrl,

	@Schema(description = "생성일", example = "2021-07-01T00:00:00", requiredMode = REQUIRED)
	LocalDateTime createdAt,

	@Schema(description = "수정일", example = "2021-07-01T00:00:00", requiredMode = REQUIRED)
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
