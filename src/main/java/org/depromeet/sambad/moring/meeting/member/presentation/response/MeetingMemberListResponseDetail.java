package org.depromeet.sambad.moring.meeting.member.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.file.presentation.annotation.FullFileUrl;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberRole;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingMemberListResponseDetail(
	@Schema(description = "모임원 ID", example = "1", requiredMode = REQUIRED)
	Long meetingMemberId,

	@Schema(description = "모임원 이름", example = "이한음", requiredMode = REQUIRED)
	String name,

	@FullFileUrl
	@Schema(description = "모임원 프로필 이미지 URL", example = "https://example.com", requiredMode = REQUIRED)
	String profileImageFileUrl,

	@Schema(description = "모임원 역할", example = "OWNER", requiredMode = REQUIRED)
	MeetingMemberRole role,

	@Schema(description = "서로 손 흔들어 인사하기를 수행했는지 여부", example = "true", requiredMode = REQUIRED)
	boolean isHandWaved
) {
	public static MeetingMemberListResponseDetail from(MeetingMember member, List<MeetingMember> handWavedMembers) {
		return new MeetingMemberListResponseDetail(
			member.getId(),
			member.getName(),
			member.getProfileImageUrl(),
			member.getRole(),
			handWavedMembers.contains(member)
		);
	}
}
