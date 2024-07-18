package org.depromeet.sambad.moring.meeting.question.presentation;

import org.depromeet.sambad.moring.meeting.question.application.MeetingQuestionService;
import org.depromeet.sambad.moring.meeting.question.presentation.request.MeetingQuestionRequest;
import org.depromeet.sambad.moring.meeting.question.presentation.response.ActiveMeetingQuestionResponse;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

@Tag(name = "MeetingQuestion", description = "모임 내 질문 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MeetingQuestionController {

	private final MeetingQuestionService meetingQuestionService;

	@Operation(summary = "다음 릴레이 질문 등록", description = "모임의 다음 릴레이 질문과 질문인을 저장합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201",
			content = @Content(schema = @Schema(implementation = Object.class))),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_QUESTION"),
		@ApiResponse(responseCode = "409", description = "DUPLICATE_MEETING_QUESTION / INVALID_MEETING_MEMBER_TARGET")
	})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/meeting-questions/new")
	public ResponseEntity<Object> save(
		@UserId Long userId,
		@Valid @RequestBody MeetingQuestionRequest request
	) {
		meetingQuestionService.save(userId, request);
		return ResponseEntity.created(null).build();
	}

	@Operation(summary = "현재 진행 중인 릴레이 질문 조회")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			content = @Content(schema = @Schema(implementation = ActiveMeetingQuestionResponse.class)),
			description = "진행 중인 릴레이 질문을 반환하거나, 진행 중인 질문이 없는 경우 null 을 반환합니다.")
	})
	@GetMapping("/meeting-questions/active")
	public ResponseEntity<Object> findActiveOne(
		@UserId Long userId
	) {
		ActiveMeetingQuestionResponse activeOne = meetingQuestionService.findActiveOne(userId);
		return ResponseEntity.ok().body(activeOne);
	}
}
