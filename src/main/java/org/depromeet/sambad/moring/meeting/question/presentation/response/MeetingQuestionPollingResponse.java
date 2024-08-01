package org.depromeet.sambad.moring.meeting.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingQuestionPollingResponse(
	@Schema(description = "새로운 질문의 질문인의 질문 등록 여부", example = "true",
		requiredMode = REQUIRED)
	Boolean isQuestionRegistered,

	@Schema(description = "로그인한 유저가 새로운 질문의 질문인인지 여부", example = "true",
		requiredMode = REQUIRED)
	Boolean isTargetMember
) {

	public static MeetingQuestionPollingResponse newQuestionOf(MeetingQuestion newQuestion, MeetingMember loginMember) {
		return new MeetingQuestionPollingResponse(newQuestion.getQuestion() == null ? false : true,
			newQuestion.getTargetMember().equals(loginMember) ? true : false);
	}
}