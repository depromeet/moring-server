package org.depromeet.sambad.moring.meeting.comment.presentation.reply;

import org.depromeet.sambad.moring.meeting.comment.application.reply.MeetingQuestionCommentReplyService;
import org.depromeet.sambad.moring.meeting.comment.presentation.reply.request.MeetingQuestionCommentReplyRequest;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "MeetingQuestionCommentReply", description = "릴레이 질문 코멘트 답글 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MeetingQuestionCommentReplyController {
	private final MeetingQuestionCommentReplyService meetingQuestionCommentReplyService;

	@Operation(summary = "릴레이 질문 코멘트 답글 등록", description = "릴레이 질문 코멘트의 답글을 작성합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201",
			content = @Content(schema = @Schema(implementation = Object.class))),
		@ApiResponse(responseCode = "403", description = "USER_NOT_MEMBER_OF_MEETING"),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_MEETING_QUESTION_COMMENT"),
	})
	@PostMapping("/meeting/questions/comments/replies")
	public ResponseEntity<Object> saveCommentReply(
		@UserId Long userId,
		@Valid @RequestBody MeetingQuestionCommentReplyRequest request
	) {
		meetingQuestionCommentReplyService.save(userId, request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "릴레이 질문 코멘트 답글 삭제", description = "릴레이 질문 코멘트의 답글을 삭제합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			content = @Content(schema = @Schema(implementation = Object.class))),
		@ApiResponse(responseCode = "400", description = "INVALID_COMMENT_REPLY_WRITER"),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_MEETING_QUESTION_COMMENT_REPLY")
	})
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/meetings/questions/comments/replies/{meetingQuestionCommentReplyId}")
	public ResponseEntity<Object> deleteCommentReply(
		@UserId Long userId,
		@PathVariable("meetingQuestionCommentReplyId") Long meetingQuestionCommentReplyId
	) {
		meetingQuestionCommentReplyService.delete(userId, meetingQuestionCommentReplyId);
		return ResponseEntity.noContent().build();
	}
}
