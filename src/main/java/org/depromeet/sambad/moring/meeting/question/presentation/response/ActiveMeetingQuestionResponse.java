package org.depromeet.sambad.moring.meeting.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.time.LocalDateTime;

import org.depromeet.sambad.moring.file.presentation.annotation.FullFileUrl;
import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberListResponseDetail;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ActiveMeetingQuestionResponse(
	@Schema(example = "1", description = "모임 질문 식별자", requiredMode = REQUIRED)
	Long meetingQuestionId,

	@FullFileUrl
	@Schema(example = "https://avatars.githubusercontent.com/u/173370739?v=4", description = "모임 질문 이미지 URL",
		requiredMode = REQUIRED)
	String questionImageFileUrl,

	@Schema(example = "갖고 싶은 초능력은?", description = "모임 질문 TITLE", requiredMode = REQUIRED)
	String title,

	@Schema(example = "18", description = "모임 내 질문 인덱스로 1 부터 시작합니다.", requiredMode = REQUIRED)
	int questionNumber,

	@Schema(example = "15", description = "전체 모임원 수", requiredMode = REQUIRED)
	int totalMeetingMemberCount,

	@Schema(example = "9", description = "응답한 모임원 수", requiredMode = REQUIRED)
	int responseCount,

	@Schema(example = "70", description = "참여율, 소수점 첫째 자리에서 반올림하여 반환합니다.", requiredMode = REQUIRED)
	double engagementRate,

	@Schema(example = "yyyy-MM-dd HH:mm:ss", description = "릴레이 질문 시작 시간", requiredMode = REQUIRED)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	LocalDateTime startTime,

	@Schema(example = "false", description = "로그인한 유저의 답변 유무", requiredMode = REQUIRED)
	Boolean isAnswered,

	@Schema(example = "true", description = "질문인의 질문 등록 여부", requiredMode = REQUIRED)
	Boolean isQuestionRegistered,

	@Schema(description = "질문인에 대한 정보", requiredMode = REQUIRED)
	MeetingMemberListResponseDetail targetMember
) {

	public static ActiveMeetingQuestionResponse questionNotRegisteredOf(MeetingQuestion meetingQuestion) {
		Meeting meeting = meetingQuestion.getMeeting();
		return ActiveMeetingQuestionResponse.builder()
			.meetingQuestionId(meetingQuestion.getId())
			.questionNumber(meeting.getQuestionNumber(meetingQuestion))
			.totalMeetingMemberCount(meeting.getTotalMemberCount())
			.responseCount(0)
			.engagementRate(0)
			.startTime(meetingQuestion.getStartTime())
			.isAnswered(false)
			.isQuestionRegistered(false)
			.targetMember(MeetingMemberListResponseDetail.from(meetingQuestion.getTargetMember()))
			.build();
	}

	public static ActiveMeetingQuestionResponse questionRegisteredOf(MeetingQuestion meetingQuestion,
		Boolean isAnswered) {
		Meeting meeting = meetingQuestion.getMeeting();

		return ActiveMeetingQuestionResponse.builder()
			.meetingQuestionId(meetingQuestion.getId())
			.questionImageFileUrl(meetingQuestion.getQuestion().getQuestionImageUrl())
			.title(meetingQuestion.getQuestion().getTitle())
			.questionNumber(meeting.getQuestionNumber(meetingQuestion))
			.totalMeetingMemberCount(meeting.getTotalMemberCount())
			.responseCount(meetingQuestion.getResponseCount())
			.engagementRate(meeting.calculateEngagementRate(meetingQuestion))
			.startTime(meetingQuestion.getStartTime())
			.isAnswered(isAnswered)
			.isQuestionRegistered(true)
			.targetMember(MeetingMemberListResponseDetail.from(meetingQuestion.getTargetMember()))
			.build();
	}
}