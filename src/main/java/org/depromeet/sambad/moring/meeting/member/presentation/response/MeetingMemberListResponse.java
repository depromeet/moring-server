package org.depromeet.sambad.moring.meeting.member.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingMemberListResponse(
	@Schema(
		description = "모임원 목록",
		example = "[{\"meetingMemberId\":1,\"name\":\"이한음\",\"profileImageFileUrl\":\"https://example.com\",\"role\":\"OWNER\"}]",
		requiredMode = REQUIRED
	)
	List<MeetingMemberListResponseDetail> contents
) {

	public static MeetingMemberListResponse from(List<MeetingMember> members) {
		List<MeetingMemberListResponseDetail> memberResponses = members.stream()
			.sorted()
			.map(MeetingMemberListResponseDetail::from)
			.toList();

		return new MeetingMemberListResponse(memberResponses);
	}
}
