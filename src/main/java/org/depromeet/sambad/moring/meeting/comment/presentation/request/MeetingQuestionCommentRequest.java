package org.depromeet.sambad.moring.meeting.comment.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MeetingQuestionCommentRequest(
	@Schema(description = "릴레이 질문 ID", example = "1")
	@NotNull
	Long meetingQuestionId,

	@Schema(description = "릴레이 질문 코멘트 내용", example = "릴레이 질문 코멘트 예시 텍스트 입니다.")
	@NotNull
	String content
) {
}
