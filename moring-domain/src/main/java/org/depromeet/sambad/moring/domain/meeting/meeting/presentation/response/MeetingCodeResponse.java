package org.depromeet.sambad.moring.domain.meeting.meeting.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import org.depromeet.sambad.moring.domain.meeting.meeting.domain.Meeting;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingCodeResponse(
	@Schema(description = "초대 코드", example = "ABCDEF", requiredMode = REQUIRED)
	String code
) {
	public static MeetingCodeResponse from(Meeting meeting) {
		return new MeetingCodeResponse(meeting.getCode());
	}
}
