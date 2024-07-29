package org.depromeet.sambad.moring.meeting.member.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.meeting.member.domain.Hobby;

import io.swagger.v3.oas.annotations.media.Schema;

public record HobbyResponse(
	@Schema(
		example = "[{\"hobbyId\": 1, \"content\": \"💩 똥\"}]",
		description = "모임원 취미 목록",
		requiredMode = REQUIRED
	)
	List<HobbyDetail> contents
) {

	record HobbyDetail(
		@Schema(example = "1", description = "모임원 취미 ID", requiredMode = REQUIRED)
		Long hobbyId,

		@Schema(example = "💩 똥", description = "모임원 취미 내용", requiredMode = REQUIRED)
		String content
	) {
	}

	public static HobbyResponse from(List<Hobby> hobbies) {
		return new HobbyResponse(
			hobbies.stream()
				.map(meetingType -> new HobbyDetail(meetingType.getId(), meetingType.getContent()))
				.toList()
		);
	}
}
