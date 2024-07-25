package org.depromeet.sambad.moring.meeting.question.presentation.response;

import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.question.presentation.response.QuestionResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingQuestionAndAnswerListResponse(
	@Schema(description = "모임의 질문 ID", example = "!")
	Long meetingQuestionId,
	@Schema(description = "질문과 답 관련 응답")
	QuestionResponse content
) {
	public static MeetingQuestionAndAnswerListResponse of(MeetingQuestion meetingQuestion) {
		return new MeetingQuestionAndAnswerListResponse(
			meetingQuestion.getId(),
			QuestionResponse.from(meetingQuestion.getQuestion())
		);
	}
}
