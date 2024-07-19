package org.depromeet.sambad.moring.meeting.comment.presentation;

import org.depromeet.sambad.moring.meeting.comment.application.MeetingQuestionCommentService;
import org.depromeet.sambad.moring.meeting.comment.presentation.request.MeetingQuestionCommentRequest;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/meeting-questions/comment")
	public ResponseEntity<Object> saveComment(
		@UserId Long userId,
		@Valid @RequestBody MeetingQuestionCommentRequest request
	) {
		meetingQuestionCommentService.save(userId, request);
		return ResponseEntity.created(null).build();
	}
}
