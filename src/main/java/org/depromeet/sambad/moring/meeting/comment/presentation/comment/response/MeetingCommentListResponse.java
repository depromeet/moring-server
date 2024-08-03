package org.depromeet.sambad.moring.meeting.comment.presentation.comment.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import org.depromeet.sambad.moring.meeting.comment.domain.comment.MeetingQuestionComment;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingCommentListResponse(
	@Schema(
		description = "릴레이질문 코멘트 목록",
		example = "[{\"meetingQuestionCommentId\":1,\"content\":\"코멘트 예시 입니다.\",\"writer\":{\"meetingMemberId\":1,\"name\":\"이한음\",\"profileImageFileUrl\":\"https://example.com\",\"role\":\"OWNER\"}}]",
		requiredMode = REQUIRED
	)
	List<MeetingCommentListResponseDetail> contents
) {
	public static MeetingCommentListResponse from(List<MeetingQuestionComment> comments) {
		return new MeetingCommentListResponse(MeetingCommentListResponseDetail.from(comments));
	}

	public ResponseEntity<MeetingCommentListResponse> toResponseEntity() {
		return contents.isEmpty()
			? ResponseEntity.noContent().build()
			: ResponseEntity.ok(this);
	}
}
