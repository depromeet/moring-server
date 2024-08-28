package org.depromeet.sambad.moring.domain.meeting.meeting.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import org.depromeet.sambad.moring.domain.meeting.meeting.domain.Meeting;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingPersistResponse(
	@Schema(example = "1", description = "모임 ID", requiredMode = REQUIRED)
	Long meetingId,

	@Schema(example = "0AF781", description = "모임 초대 코드", requiredMode = REQUIRED)
	String inviteCode
) {

	public static MeetingPersistResponse from(Meeting meeting) {
		return new MeetingPersistResponse(meeting.getId(), meeting.getCode());
	}
}
