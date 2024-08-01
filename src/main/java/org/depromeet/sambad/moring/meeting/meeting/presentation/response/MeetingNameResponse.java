package org.depromeet.sambad.moring.meeting.meeting.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingNameResponse(
	@Schema(description = "모임명", example = "삼밧드의 모험", requiredMode = REQUIRED)
	String name
) {
	public static MeetingNameResponse from(Meeting meeting) {
		return new MeetingNameResponse(meeting.getName());
	}
}
