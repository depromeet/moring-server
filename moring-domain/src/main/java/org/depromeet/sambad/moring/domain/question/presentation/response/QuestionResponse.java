package org.depromeet.sambad.moring.domain.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.domain.file.presentation.annotation.FullFileUrl;
import org.depromeet.sambad.moring.domain.meeting.answer.presentation.response.AnswerResponse;
import org.depromeet.sambad.moring.domain.question.domain.Question;
import org.depromeet.sambad.moring.domain.question.domain.QuestionType;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuestionResponse(
	@Schema(description = "질문 ID", example = "1", requiredMode = REQUIRED)
	Long questionId,

	@Schema(description = "질문 유형",
		examples = {"SINGLE_CHOICE", "MULTIPLE_SHORT_CHOICE", "MULTIPLE_DESCRIPTIVE_CHOICE"},
		requiredMode = REQUIRED)
	QuestionType questionType,

	@FullFileUrl
	@Schema(description = "질문 이미지 URL", example = "https://example.com", requiredMode = REQUIRED)
	String questionImageFileUrl,

	@Schema(description = "질문 제목", example = "갖고 싶은 초능력은?", requiredMode = REQUIRED)
	String title,

	@Schema(description = "질문에 대한 답변 목록", requiredMode = REQUIRED)
	List<AnswerResponse> answers
) {

	public static QuestionResponse from(final Question question) {
		return new QuestionResponse(
			question.getId(),
			question.getQuestionType(),
			question.getQuestionImageUrl(),
			question.getTitle(),
			AnswerResponse.from(question.getAnswers())
		);
	}
}
