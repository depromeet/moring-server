package org.depromeet.sambad.moring.meeting.question.presentation.response;

import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberResponse;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ActiveMeetingQuestionResponse(
	@Schema(example = "1", description = "모임 질문 식별자")
	Long meetingQuestionId,
	@Schema(example = "https://avatars.githubusercontent.com/u/173370739?v=4", description = "모임 질문 이미지 URL")
	String questionImageFileUrl,
	@Schema(example = "갖고 싶은 초능력은?", description = "모임 질문 TITLE")
	String title,
	@Schema(example = "18", description = "모임 내 질문 인덱스로 1 부터 시작합니다.")
	int questionNumber,
	@Schema(example = "15", description = "전체 모임원 수")
	int totalMeetingMemberCount,
	@Schema(example = "9", description = "응답한 모임원 수")
	int responseCount,
	@Schema(example = "70", description = "참여율, 소수점 첫째 자리에서 반올림하여 반환합니다.")
	double engagementRate,
	@Schema(example = "false", description = "로그인한 유저의 답변 유무")
	Boolean isAnswered,
	@Schema(description = "질문인에 대한 정보")
	MeetingMemberResponse targetMember
) {

	public static ActiveMeetingQuestionResponse of(MeetingQuestion meetingQuestion, Boolean isAnswered) {
		Meeting meeting = meetingQuestion.getMeeting();

		return ActiveMeetingQuestionResponse.builder()
			.meetingQuestionId(meetingQuestion.getId())
			.questionImageFileUrl(meetingQuestion.getQuestion().getQuestionImageUrl())
			.title(meetingQuestion.getQuestion().getTitle())
			.questionNumber(meeting.getQuestionNumber(meetingQuestion))
			.totalMeetingMemberCount(meeting.getTotalMemberCount())
			.responseCount(meetingQuestion.getResponseCount())
			.engagementRate(meeting.calculateEngagementRate(meetingQuestion))
			.isAnswered(isAnswered)
			.targetMember(MeetingMemberResponse.from(meetingQuestion.getTargetMember()))
			.build();
	}
}