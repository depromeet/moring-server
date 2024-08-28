package org.depromeet.sambad.moring.domain.meeting.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import org.depromeet.sambad.moring.domain.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.domain.question.domain.QuestionType;
import org.depromeet.sambad.moring.domain.question.presentation.response.QuestionResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingQuestionAndAnswerListResponse(
	@Schema(description = "모임의 질문 ID", example = "!", requiredMode = REQUIRED)
	Long meetingQuestionId,

	@Schema(description = "질문 유형",
		examples = {"SINGLE_CHOICE", "MULTIPLE_SHORT_CHOICE", "MULTIPLE_DESCRIPTIVE_CHOICE"},
		requiredMode = REQUIRED)
	QuestionType questionType,

	@Schema(description = "질문과 답 관련 응답", requiredMode = REQUIRED)
	QuestionResponse content
) {
	public static MeetingQuestionAndAnswerListResponse of(MeetingQuestion meetingQuestion) {
		return new MeetingQuestionAndAnswerListResponse(
			meetingQuestion.getId(),
			meetingQuestion.getQuestion().getQuestionType(),
			QuestionResponse.from(meetingQuestion.getQuestion())
		);
	}
}