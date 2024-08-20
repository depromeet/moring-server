package org.depromeet.sambad.moring.user.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import org.depromeet.sambad.moring.file.presentation.annotation.FullFileUrl;
import org.depromeet.sambad.moring.user.domain.User;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponse(
	@Schema(description = "유저 이름", example = "홍길동", requiredMode = REQUIRED)
	String name,

	@Schema(description = "유저 이메일", example = "example@abc.com", requiredMode = REQUIRED)
	String email,

	@FullFileUrl
	@Schema(description = "프로필 이미지 경로", example = "https://example.com/profile.jpg", requiredMode = REQUIRED)
	String profileImageFileUrl,

	@Schema(example = "1722707775774", description = "생성일", requiredMode = REQUIRED)
	Long createdAt,

	@Schema(example = "1722707775774", description = "수정일", requiredMode = REQUIRED)
	Long updatedAt
) {

	public static UserResponse from(User user) {
		return new UserResponse(
			user.getName(),
			user.getEmail(),
			user.getProfileImageFileUrl(),
			user.getCreatedAtWithEpochMilli(),
			user.getUpdatedAtWithEpochMilli()
		);
	}
}
