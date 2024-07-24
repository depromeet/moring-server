package org.depromeet.sambad.moring.meeting.answer.presentation;

import org.depromeet.sambad.moring.meeting.answer.application.MeetingAnswerService;
import org.depromeet.sambad.moring.meeting.answer.presentation.request.MeetingAnswerRequest;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@Tag(name = "모임원의 답변", description = "모임 내 릴레이 질문에 대한 답변 api / 담당자 : 이한음")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MeetingAnswerController {

	private final MeetingAnswerService meetingAnswerService;

	@Operation(summary = "모임원 답변 등록")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "meetingQuestion(모임원 답변) 등록 성공"),
		@ApiResponse(responseCode = "404", description = "MEETING_MEMBER_NOT_FOUND / NOT_FOUND_MEETING_QUESTION / "
			+ "NOT_FOUND_ANSWER"),
		@ApiResponse(responseCode = "409", description = "DUPLICATE_MEETING_ANSWER")
	})
	@PostMapping("/meetings/{meetingId}/answers")
	public ResponseEntity<Object> save(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable Long meetingId,
		@Valid @RequestBody MeetingAnswerRequest request
	) {
		meetingAnswerService.save(userId, meetingId, request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}