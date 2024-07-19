package org.depromeet.sambad.moring.meeting.answer.presentation;

import org.depromeet.sambad.moring.meeting.answer.application.MeetingAnswerService;
import org.depromeet.sambad.moring.meeting.answer.presentation.request.MeetingAnswerRequest;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "MeetingAnswer", description = "모임 내 답변 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MeetingAnswerController {

	private final MeetingAnswerService meetingAnswerService;

	@Operation(summary = "모임원 답변 등록")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "meetingQuestion(모임원 답변) 등록 성공"),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_MEETING_QUESTION / NOT_FOUND_ANSWER"),
		@ApiResponse(responseCode = "409", description = "DUPLICATE_MEETING_ANSWER")
	})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/meeting-answers/new")
	public ResponseEntity<Object> save(
		@UserId Long userId,
		@Valid @RequestBody MeetingAnswerRequest request
	) {
		meetingAnswerService.save(userId, request);
		return ResponseEntity.created(null).build();
	}
}