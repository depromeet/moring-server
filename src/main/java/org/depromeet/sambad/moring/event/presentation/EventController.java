package org.depromeet.sambad.moring.event.presentation;

import org.depromeet.sambad.moring.event.application.EventService;
import org.depromeet.sambad.moring.event.presentation.response.EventListResponse;
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

@Tag(name = "알림 조회 관련 API", description = "알림 관련 CURD 및 모달 폴링 담당 / 담당자 : 이한음, 권기준")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/events")
public class EventController {

	private final EventService eventService;
	private final EventFacade eventFacade;

	@Operation(summary = "알림 목록 조회", description = """
		<h2>Description</h2>
		모임원이 수령해야 하는 알림 목록을 조회합니다.
				
		<h2>Event Type List</h2>
		* <b>QUESTION_REGISTERED</b>: 릴레이 질문이 등록되어 답변 가능한 경우
		* <b>TARGET_MEMBER</b>: 릴레이 질문 등록 대상자로 선정된 경우
		* <b>HAND_WAVING_REQUESTED</b>: 손 흔들기 요청이 들어온 경우
				
		<h2>Additional Data</h2>
		Event Type에 따라 addionalData가 다르게 반환됩니다.
		* <b>HAND_WAVING_REQUESTED</b>: {"handWavingId": 1, "status": "ACCEPTED"}
		  * <b>status == REQUESTED</b> 일 떄에만 수락 / 거절 버튼 표시되면 될 것 같습니다."""
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "알림 목록 조회 성공"),
		@ApiResponse(responseCode = "403", description = "USER_NOT_MEMBER_OF_MEETING")
	})
	@GetMapping("/meetings/{meetingId}")
	public ResponseEntity<EventListResponse> getEvents(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true)
		@PathVariable("meetingId") @Positive Long meetingId
	) {
		EventListResponse response = eventFacade.getEventsResponse(userId, meetingId);
		return ResponseEntity.ok(response);
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
	@GetMapping(value = {"/meetings/{meetingId}/polling", "/{meetingId}"})
	public ResponseEntity<PollingEventListResponse> getActiveEvents(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") @Positive Long meetingId
	) {
		PollingEventListResponse response = eventService.getActiveEvents(userId, meetingId);
		return response.toResponseEntity();
	}

	@Operation(summary = "이벤트 비활성화", description = "해당 이벤트 알림을 비활성화 합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "이벤트 비활성화 성공"),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_EVENT")
	})
	@PatchMapping("/{eventId}/inactivate")
	public ResponseEntity<Object> inactivateEvent(
		@Parameter(description = "이벤트 ID", example = "1", required = true) @PathVariable("eventId") @Positive Long eventId
	) {
		eventService.inactivate(eventId);
		return ResponseEntity.ok().build();
	}
}
