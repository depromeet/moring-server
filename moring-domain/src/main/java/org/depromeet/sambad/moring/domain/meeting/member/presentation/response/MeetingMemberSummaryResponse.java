package org.depromeet.sambad.moring.domain.meeting.member.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import org.depromeet.sambad.moring.domain.file.presentation.annotation.FullFileUrl;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMemberRole;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingMemberSummaryResponse(
	@Schema(description = "모임원 ID", example = "1", requiredMode = REQUIRED)
	Long meetingMemberId,

	@Schema(description = "모임원 이름", example = "이한음", requiredMode = REQUIRED)
	String name,

	@FullFileUrl
	@Schema(description = "모임원 프로필 이미지 URL", example = "https://example.com", requiredMode = REQUIRED)
	String profileImageFileUrl,

	@Schema(description = "모임원 역할", example = "OWNER", requiredMode = REQUIRED)
	MeetingMemberRole role
) {
	public static MeetingMemberSummaryResponse from(MeetingMember member) {
		if (member == null) {
			return null;
		}
		return new MeetingMemberSummaryResponse(
			member.getId(),
			member.getName(),
			member.getProfileImageUrl(),
			member.getRole()
		);
	}
}
