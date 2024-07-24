package org.depromeet.sambad.moring.meeting.comment.presentation.reply.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MeetingQuestionCommentReplyRequest(
	@Schema(description = "릴레이 질문 코멘트 답글 내용", example = "코멘트 답글 입니다.")
	@NotBlank
	@Size(max = 10)
	String content
) {
}
