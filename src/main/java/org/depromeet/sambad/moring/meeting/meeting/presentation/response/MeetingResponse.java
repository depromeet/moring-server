package org.depromeet.sambad.moring.meeting.meeting.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingResponse(
	@Schema(description = "가입 되어 있는 모임의 정보 목록", requiredMode = REQUIRED)
	List<MeetingResponseDetail> meetings
) {
	public static MeetingResponse from(List<Meeting> meetings) {
		return new MeetingResponse(
			meetings.stream()
				.map(meeting -> new MeetingResponseDetail(meeting.getId(), meeting.getName()))
				.toList()
		);
	}

	public record MeetingResponseDetail(
		@Schema(description = "모임 ID", example = "1", requiredMode = REQUIRED)
		Long meetingId,

		@Schema(description = "모임명", example = "1", requiredMode = REQUIRED)
		String name
	) {
	}
}
