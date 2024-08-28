package org.depromeet.sambad.moring.domain.meeting.member.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import org.depromeet.sambad.moring.domain.meeting.member.domain.Hobby;

import io.swagger.v3.oas.annotations.media.Schema;

public record HobbyDetailResponse(
	@Schema(example = "1", description = "모임원 취미 ID", requiredMode = REQUIRED)
	Long hobbyId,

	@Schema(example = "💩 똥", description = "모임원 취미 내용", requiredMode = REQUIRED)
	String content
) {
	public static HobbyDetailResponse from(Hobby hobby) {
		return new HobbyDetailResponse(hobby.getId(), hobby.getContent());
	}
}
