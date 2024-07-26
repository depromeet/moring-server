package org.depromeet.sambad.moring.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import org.depromeet.sambad.moring.file.presentation.annotation.FullFileUrl;
import org.depromeet.sambad.moring.meeting.answer.presentation.response.AnswerResponse;
import org.depromeet.sambad.moring.question.domain.Question;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuestionResponse(
	@Schema(description = "질문 ID", example = "1", requiredMode = REQUIRED)
	Long questionId,
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
			question.getQuestionImageUrl(),
			question.getTitle(),
			AnswerResponse.from(question.getAnswers())
		);
	}
}
