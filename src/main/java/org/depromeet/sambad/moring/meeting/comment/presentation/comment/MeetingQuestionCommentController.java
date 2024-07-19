package org.depromeet.sambad.moring.meeting.comment.presentation.comment;

import java.util.List;

import org.depromeet.sambad.moring.meeting.comment.application.comment.MeetingQuestionCommentService;
import org.depromeet.sambad.moring.meeting.comment.presentation.comment.request.MeetingQuestionCommentRequest;
import org.depromeet.sambad.moring.meeting.comment.presentation.comment.response.MeetingQuestionCommentResponse;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

@Tag(name = "MeetingQuestionComment", description = "릴레이 질문 코멘트 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MeetingQuestionCommentController {
	private final MeetingQuestionCommentService meetingQuestionCommentService;

	@Operation(summary = "릴레이 질문 코멘트 등록", description = "모임의 릴레의 질문에 대한 코멘트를 작성합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201",
			content = @Content(schema = @Schema(implementation = Object.class))),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_QUESTION"),
	})
	@PostMapping("/meetings/questions/comments")
	public ResponseEntity<Object> saveComment(
		@UserId Long userId,
		@Valid @RequestBody MeetingQuestionCommentRequest request
	) {
		meetingQuestionCommentService.save(userId, request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "릴레이 질문에 대한 모든 코멘트 조회", description = "모임의 릴레이 질문에 대한 모든 코멘트를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			content = @Content(schema = @Schema(implementation = Object.class))),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_QUESTION")
	})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/meetings/questions/comments/{meetingQuestionId}")
	public ResponseEntity<Object> getComments(
		@PathVariable("meetingQuestionId") Long meetingQuestionId
	) {
		List<MeetingQuestionCommentResponse> comments = meetingQuestionCommentService.getAllComments(meetingQuestionId);
		return ResponseEntity.ok(comments);
	}

	@Operation(summary = "릴레이 질문 코멘트 삭제", description = "모임의 릴레이 질문 코멘트를 삭제합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			content = @Content(schema = @Schema(implementation = Object.class))),
		@ApiResponse(responseCode = "400", description = "INVALID_COMMENT_WRITER"),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_QUESTION_COMMENT")
	})
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/meetings/questions/comments/{meetingQuestionCommentId}")
	public ResponseEntity<Object> deleteComment(
		@UserId Long userId,
		@PathVariable("meetingQuestionCommentId") Long meetingQuestionCommentId
	) {
		meetingQuestionCommentService.delete(userId, meetingQuestionCommentId);
		return ResponseEntity.noContent().build();
	}
}
