package org.depromeet.sambad.moring.event.presentation;

import org.depromeet.sambad.moring.event.application.EventService;
import org.depromeet.sambad.moring.event.presentation.response.PollingEventListResponse;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Tag(name = "Polling 을 위한 이벤트 api", description = "Polling 을 위한 이벤트 api / 담당자 : 이한음")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class EventController {
	private final EventService eventService;

	@Operation(summary = "이벤트 비활성화", description = "해당 이벤트 알림을 비활성화 합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "이벤트 비활성화 성공"),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_EVENT")
	})
	@PatchMapping("/events/{eventId}/inactivate")
	public ResponseEntity<Object> inactivateEvent(
		@Parameter(description = "이벤트 ID", example = "1", required = true) @PathVariable("eventId") @Positive Long eventId
	) {
		eventService.inactivate(eventId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "사용자가 받아야 하는 이벤트 목록 조회", description = """
		사용자가 받아야 하는 이벤트의 목록을 조회합니다.
		
		Event List
		* QUESTION_REGISTERED: 릴레이 질문이 등록되어 답변 가능한 경우
		* TARGET_MEMBER: 릴레이 질문 등록 대상자로 선정된 경우
	""")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "사용자가 받아야 하는 이벤트의 목록 조회 성공"),
		@ApiResponse(responseCode = "204", description = "사용자가 받아야 하는 이벤트 없음"),
		@ApiResponse(responseCode = "403", description = "USER_NOT_MEMBER_OF_MEETING")
	})
	@GetMapping("/events/{meetingId}")
	public ResponseEntity<PollingEventListResponse> getActiveEvents(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") @Positive Long meetingId
	) {
		PollingEventListResponse response = eventService.getActiveEvents(userId, meetingId);
		return response.toResponseEntity();
	}
}
