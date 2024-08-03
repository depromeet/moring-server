package org.depromeet.sambad.moring.meeting.comment.presentation.comment.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MeetingQuestionCommentRequest(
	@Schema(description = "릴레이 질문 ID", example = "1")
	@NotNull
	Long meetingQuestionId,

	@Schema(description = "릴레이 질문 코멘트 내용", example = "코멘트 예시")
	@NotBlank
	@Size(max = 10)
	String content
) {
}
