package org.depromeet.sambad.moring.meeting.member.presentation.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.time.LocalDate;
import java.util.List;

import org.depromeet.sambad.moring.common.domain.Gender;
import org.depromeet.sambad.moring.meeting.member.domain.MBTI;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberRole;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MeetingMemberPersistRequest(

	@Schema(description = "모임원 유형 (HOST, MEMBER)", example = "HOST", requiredMode = REQUIRED)
	@NotNull
	MeetingMemberRole role,

	@Schema(description = "모임원 이름", example = "권기준", requiredMode = REQUIRED)
	@NotBlank
	@Size(min = 2, max = 5)
	String name,

	@Schema(description = "모임원 성별 (MALE, FEMALE)", example = "FEMALE", requiredMode = REQUIRED)
	@NotNull
	Gender gender,

	@Schema(description = "모임원 생년월일", example = "1998-10-29", requiredMode = REQUIRED)
	@NotNull
	LocalDate birth,

	@Schema(description = "모임원 직업", example = "식물 가게 사장", requiredMode = REQUIRED)
	@Size(min = 1, max = 15)
	String job,

	@Schema(description = "모임원 거주지", example = "서울시 광진구 구의동", requiredMode = REQUIRED)
	@Size(min = 1, max = 15)
	String location,

	@Schema(description = "모임원 취미 ID 리스트", example = "[1, 2, 3]", requiredMode = NOT_REQUIRED)
	@Size(max = 3)
	List<Long> hobbyIds,

	@Schema(description = "모임원 MBTI", example = "ISFP", requiredMode = NOT_REQUIRED)
	MBTI mbti,

	@Schema(description = "모임원 소개", example = "안녕하세요! 저는 식물 가게 사장 권기준입니다.", requiredMode = NOT_REQUIRED)
	@Size(max = 5000)
	String introduction
) {
	@JsonIgnore
	public boolean isHost() {
		return role == MeetingMemberRole.OWNER;
	}
}
