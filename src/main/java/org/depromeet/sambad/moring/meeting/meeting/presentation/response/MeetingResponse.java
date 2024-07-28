package org.depromeet.sambad.moring.meeting.meeting.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingResponse(
	@Schema(description = "모임 ID 목록", example = "[1, 2, 3]", requiredMode = REQUIRED)
	List<Long> meetingIds
) {
	public static MeetingResponse from(List<Meeting> meetings) {
		return new MeetingResponse(
			meetings.stream()
				.map(Meeting::getId)
				.toList()
		);
	}
}
