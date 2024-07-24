package org.depromeet.sambad.moring.meeting.comment.presentation.comment;

import org.depromeet.sambad.moring.meeting.comment.application.comment.MeetingQuestionCommentService;
import org.depromeet.sambad.moring.meeting.comment.presentation.comment.request.MeetingQuestionCommentRequest;
import org.depromeet.sambad.moring.meeting.comment.presentation.comment.response.MeetingCommentListResponse;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "모임의 릴레이 질문 내 코멘트", description = "릴레이 질문 코멘트 관련 api / 담당자 : 이한음")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MeetingQuestionCommentController {

	private final MeetingQuestionCommentService meetingQuestionCommentService;

	@Operation(summary = "릴레이 질문 코멘트 등록", description = "모임의 릴레의 질문에 대한 코멘트를 작성합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201"),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_QUESTION"),
	})
	@PostMapping("/meetings/{meetingId}/questions/comments")
	public ResponseEntity<Object> saveComment(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable Long meetingId,
		@Valid @RequestBody MeetingQuestionCommentRequest request
	) {
		meetingQuestionCommentService.save(userId, meetingId, request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "릴레이 질문에 대한 모든 코멘트 조회", description = "모임의 릴레이 질문에 대한 모든 코멘트를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_QUESTION")
	})
	@GetMapping("/meetings/{meetingId}/questions/{meetingQuestionId}/comments")
	public ResponseEntity<MeetingCommentListResponse> getComments(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable Long meetingId,
		@PathVariable("meetingQuestionId") Long meetingQuestionId
	) {
		MeetingCommentListResponse comments = meetingQuestionCommentService.getAllComments(userId, meetingId,
			meetingQuestionId);
		return ResponseEntity.ok(comments);
	}

	@Operation(summary = "릴레이 질문 코멘트 삭제", description = "모임의 릴레이 질문 코멘트를 삭제합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "400", description = "INVALID_COMMENT_WRITER"),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_QUESTION_COMMENT")
	})
	@DeleteMapping("/meetings/{meetingId}/questions/{meetingQuestionId}/comments/{meetingQuestionCommentId}")
	public ResponseEntity<Object> deleteComment(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable Long meetingId,
		@PathVariable("meetingQuestionId") Long meetingQuestionId,
		@PathVariable(value = "meetingQuestionCommentId") Long meetingQuestionCommentId
	) {
		meetingQuestionCommentService.delete(userId, meetingId, meetingQuestionId, meetingQuestionCommentId);
		return ResponseEntity.noContent().build();
	}
}
