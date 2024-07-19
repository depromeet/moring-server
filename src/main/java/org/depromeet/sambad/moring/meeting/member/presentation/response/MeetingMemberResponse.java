package org.depromeet.sambad.moring.meeting.member.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.ArrayList;
import java.util.List;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;

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
		String role
	) {
		public static MeetingMemberResponseDetail from(MeetingMember member) {
			return new MeetingMemberResponseDetail(
				member.getId(),
				member.getName(),
				member.getProfileImageUrl(),
				member.getRole().name()
			);
		}
	}

	public static MeetingMemberResponse from(List<MeetingMember> members) {
		// OWNER 멤버를 먼저 필터링
		List<MeetingMemberResponseDetail> ownerMembers = members.stream()
			.filter(MeetingMember::isOwner)
			.map(MeetingMemberResponseDetail::from)
			.toList();

		// OWNER 멤버가 아닌 나머지 멤버들
		List<MeetingMemberResponseDetail> nonOwnerMembers = members.stream()
			.filter(MeetingMember::isNotOwner)
			.map(MeetingMemberResponseDetail::from)
			.toList();

		// 두 리스트를 합침 (OWNER 멤버가 먼저 나오도록)
		List<MeetingMemberResponseDetail> allMembers = new ArrayList<>();
		allMembers.addAll(ownerMembers);
		allMembers.addAll(nonOwnerMembers);

		return new MeetingMemberResponse(allMembers);
	}
}
