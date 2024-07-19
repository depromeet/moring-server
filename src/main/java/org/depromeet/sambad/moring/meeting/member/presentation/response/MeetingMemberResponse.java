package org.depromeet.sambad.moring.meeting.member.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberRole;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingMemberResponse(
	@Schema(
		description = "모임원 목록",
		example = "[{\"id\":1,\"name\":\"이한음\",\"profileImageFileUrl\":\"https://example.com\",\"role\":\"OWNER\"}]",
		requiredMode = REQUIRED)
	List<MeetingMemberResponseDetail> contents
) {

	public record MeetingMemberResponseDetail(
		@Schema(description = "모임원 ID", example = "1", requiredMode = REQUIRED)
		Long id,
		@Schema(description = "모임원 이름", example = "이한음", requiredMode = REQUIRED)
		String name,
		@Schema(description = "모임원 프로필 이미지 URL", example = "https://example.com", requiredMode = REQUIRED)
		String profileImageFileUrl,
		@Schema(description = "모임원 역할", example = "OWNER", requiredMode = REQUIRED)
		MeetingMemberRole role
	) {
		public static MeetingMemberResponseDetail from(MeetingMember member) {
			return new MeetingMemberResponseDetail(
				member.getId(),
				member.getName(),
				member.getProfileImageUrl(),
				member.getRole()
			);
		}
	}

	public static MeetingMemberResponse from(List<MeetingMember> members) {
		List<MeetingMemberResponseDetail> memberResponses = members.stream()
			.sorted()
			.map(MeetingMemberResponseDetail::from)
			.toList();

		return new MeetingMemberResponse(memberResponses);
	}
}