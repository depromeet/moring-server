package org.depromeet.sambad.moring.domain.meeting.meeting.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.domain.meeting.meeting.domain.MeetingType;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingTypeResponse(
	@Schema(example = "[{\"meetingTypeId\": 1, \"content\": \"💩 똥\"}]", description = "모임 유형 목록", requiredMode = REQUIRED)
	List<MeetingTypeDetail> contents
) {

	record MeetingTypeDetail(
		@Schema(example = "1", description = "모임 타입 ID", requiredMode = REQUIRED)
		Long meetingTypeId,

		@Schema(example = "💩 똥", description = "모임 유형", requiredMode = REQUIRED)
		String content
	) {
	}

	public static MeetingTypeResponse from(List<MeetingType> meetingTypes) {
		return new MeetingTypeResponse(
			meetingTypes.stream()
				.map(meetingType -> new MeetingTypeDetail(meetingType.getId(), meetingType.getContent()))
				.toList()
		);
	}
}
