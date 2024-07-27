package org.depromeet.sambad.moring.meeting.member.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingMemberPersistResponse(
	@Schema(description = "모임 ID", example = "1", requiredMode = REQUIRED)
	Long meetingId,

	@Schema(description = "모임 멤버 ID", example = "1", requiredMode = REQUIRED)
	Long meetingMemberId
) {
	public static MeetingMemberPersistResponse from(MeetingMember meetingMember) {
		return new MeetingMemberPersistResponse(
			meetingMember.getMeeting().getId(),
			meetingMember.getId()
		);
	}
}
