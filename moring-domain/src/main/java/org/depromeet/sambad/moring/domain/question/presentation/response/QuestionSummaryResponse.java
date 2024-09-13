package org.depromeet.sambad.moring.domain.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import org.depromeet.sambad.moring.domain.file.presentation.annotation.FullFileUrl;
import org.depromeet.sambad.moring.domain.question.domain.QuestionSummaryDto;

import com.querydsl.core.annotations.QueryProjection;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuestionSummaryResponse(
	@Schema(description = "질문 ID", example = "1", requiredMode = REQUIRED)
	Long questionId,

	@FullFileUrl
	@Schema(description = "질문 이미지 URL", example = "https://example.com", requiredMode = REQUIRED)
	String questionImageFileUrl,

	@Schema(description = "질문 제목 정보", requiredMode = REQUIRED)
	QuestionTitleResponse questionTitle,

	@Schema(description = "질문 제목", example = "갖고 싶은 초능력은?", requiredMode = REQUIRED)
	String title,

	@Schema(description = "질문 사용 횟수", example = "3", requiredMode = REQUIRED)
	Long usedCount
) {

	public static QuestionSummaryResponse from(QuestionSummaryDto dto) {
		return new QuestionSummaryResponse(
			dto.question().getId(),
			dto.question().getQuestionImageFile().getPhysicalPath(),
			QuestionTitleResponse.from(dto.question()),
			dto.question().getFullTitle(),
			dto.usedCount()
		);
	}

}
