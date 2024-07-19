package org.depromeet.sambad.moring.meeting.meeting.presentation.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MeetingPersistRequest(

	@Schema(description = "모임 이름", example = "반갑다 친구들아", requiredMode = REQUIRED)
	@NotBlank
	@Size(min = 2, max = 10)
	String name,

	@Schema(description = "모임 유형의 ID 목록", example = "[1,3,5]", requiredMode = REQUIRED)
	@NotNull
	@Size(max = 2)
	List<Long> typeIds
) {
}
