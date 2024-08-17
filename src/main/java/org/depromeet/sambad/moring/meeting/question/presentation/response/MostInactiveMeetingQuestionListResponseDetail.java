package org.depromeet.sambad.moring.meeting.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.Optional;

import org.depromeet.sambad.moring.answer.domain.Answer;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MostInactiveMeetingQuestionListResponseDetail(
	@Schema(example = "1", description = "모임 질문 식별자", requiredMode = REQUIRED)
	Long meetingQuestionId,

	@Schema(example = "갖고 싶은 초능력은?", description = "모임 질문 TITLE", requiredMode = REQUIRED)
	String title,

	@Schema(example = "순간이동", description = "가장 많이 선택한 답변", requiredMode = NOT_REQUIRED)
	String content,

	@Schema(example = "70", description = "참여율, 소수점 첫째 자리에서 반올림하여 반환합니다.", requiredMode = REQUIRED)
	double engagementRate,

	@Schema(example = "1722707775774", description = "릴레이 질문 시작 시간", requiredMode = REQUIRED)
	Long startTime
) {

	public static MostInactiveMeetingQuestionListResponseDetail of(MeetingQuestion meetingQuestion,
		Optional<Answer> bestAnswer) {
		return MostInactiveMeetingQuestionListResponseDetail.builder()
			.meetingQuestionId(meetingQuestion.getId())
			.title(meetingQuestion.getQuestion().getTitle())
			.content(bestAnswer.isPresent() ? bestAnswer.get().getContent() : null)
			.engagementRate(meetingQuestion.calculateEngagementRate())
			.startTime(meetingQuestion.getEpochMilliStartTime())
			.build();
	}
}
