package org.depromeet.sambad.moring.meeting.question.presentation.response;

import org.depromeet.sambad.moring.answer.domain.Answer;
import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MostInactiveMeetingQuestionListResponseDetail(
	@Schema(example = "1", description = "모임 질문 식별자")
	Long meetingQuestionId,
	@Schema(example = "갖고 싶은 초능력은?", description = "모임 질문 TITLE")
	String title,
	@Schema(example = "순간이동", description = "가장 많이 선택한 답변")
	String content,
	@Schema(example = "70", description = "참여율, 소수점 첫째 자리에서 반올림하여 반환합니다.")
	double engagementRate
) {

	public static MostInactiveMeetingQuestionListResponseDetail of(MeetingQuestion meetingQuestion, Answer bestAnswer) {
		Meeting meeting = meetingQuestion.getMeeting();

		return MostInactiveMeetingQuestionListResponseDetail.builder()
			.meetingQuestionId(meetingQuestion.getId())
			.title(meetingQuestion.getQuestion().getTitle())
			.content(bestAnswer.getContent())
			.engagementRate(meeting.calculateEngagementRate(meetingQuestion))
			.build();
	}
}
