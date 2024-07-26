package org.depromeet.sambad.moring.meeting.member.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.time.LocalDate;
import java.util.List;

import org.depromeet.sambad.moring.common.domain.Gender;
import org.depromeet.sambad.moring.file.presentation.annotation.FullFileUrl;
import org.depromeet.sambad.moring.meeting.member.domain.MBTI;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberRole;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingMemberResponse(
	@Schema(description = "모임원 ID", example = "1", requiredMode = REQUIRED)
	Long id,

	@Schema(description = "모임원 이름", example = "이한음", requiredMode = REQUIRED)
	String name,

	@FullFileUrl
	@Schema(description = "모임원 프로필 이미지 URL", example = "https://example.com", requiredMode = REQUIRED)
	String profileImageFileUrl,

	@Schema(description = "모임원 역할", example = "OWNER", requiredMode = REQUIRED)
	MeetingMemberRole role,

	@Schema(description = "모임원 성별 (MALE, FEMALE)", example = "FEMALE", requiredMode = REQUIRED)
	Gender gender,

	@Schema(description = "모임원 생년월일", example = "1998-10-29", requiredMode = REQUIRED)
	LocalDate birth,

	@Schema(description = "모임원 직업", example = "식물 가게 사장", requiredMode = REQUIRED)
	String job,

	@Schema(description = "모임원 거주지", example = "서울시 광진구 구의동", requiredMode = REQUIRED)
	String location,

	@Schema(description = "모임원 취미", example = "[\"독서\", \"등산\"]", requiredMode = NOT_REQUIRED)
	List<String> hobbies,

	@Schema(description = "모임원 MBTI", example = "ISFP", requiredMode = NOT_REQUIRED)
	MBTI mbti,

	@Schema(description = "모임원 소개", example = "안녕하세요! 저는 식물 가게 사장 권기준입니다.", requiredMode = NOT_REQUIRED)
	String introduction
) {
	public static MeetingMemberResponse from(MeetingMember member) {
		return new MeetingMemberResponse(
			member.getId(),
			member.getName(),
			member.getProfileImageUrl(),
			member.getRole(),
			member.getGender(),
			member.getBirth(),
			member.getJob(),
			member.getLocation(),
			member.getHobbies(),
			member.getMbti(),
			member.getIntroduction()
		);
	}
}
