package org.depromeet.sambad.moring.domain.meeting.member.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.domain.meeting.member.domain.Hobby;

import io.swagger.v3.oas.annotations.media.Schema;

public record HobbyResponse(
	@Schema(
		example = "[{\"hobbyId\": 1, \"content\": \"💩 똥\"}]",
		description = "모임원 취미 목록",
		requiredMode = REQUIRED
	)
	List<HobbyDetailResponse> contents
) {

	public static HobbyResponse from(List<Hobby> hobbies) {
		return new HobbyResponse(
			hobbies.stream()
				.map(HobbyDetailResponse::from)
				.toList()
		);
	}
}
