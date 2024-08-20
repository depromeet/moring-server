package org.depromeet.sambad.moring.meeting.handWaving.presentation;

import org.depromeet.sambad.moring.meeting.handWaving.application.HandWavingService;
import org.depromeet.sambad.moring.meeting.handWaving.presentation.request.HandWavingRequest;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
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

@Tag(name = "손 흔들어 인사하기", description = "손 흔들어 인사하기 관련 api / 담당자 : 이한음")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/meetings/{meetingId}/handWaving")
public class HandWavingController {
	private final HandWavingService handWavingService;

	@Operation(summary = "손 흔들어 인사하기", description = "모임원에게 손을 흔들어 인사합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "손 흔들기 성공"),
		@ApiResponse(responseCode = "403", description = "USER_NOT_MEMBER_OF_MEETING"),
		@ApiResponse(responseCode = "404", description = "MEETING_MEMBER_NOT_FOUND"),
	})
	@PostMapping
	public ResponseEntity<Void> sendHandWaving(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") Long meetingId,
		@Valid @RequestBody HandWavingRequest request
	) {
		handWavingService.sendHandWaving(userId, meetingId, request);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "나도 인사 건네기", description = "손을 흔들어 인사한 모임원에게 나도 인사를 건넵니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "나도 인사 건네기 성공"),
		@ApiResponse(responseCode = "403", description = "USER_NOT_MEMBER_OF_MEETING"),
		@ApiResponse(responseCode = "404", description = "HAND_WAVING_NOT_FOUND"),
	})
	@PatchMapping("/{handWavingId}/resend")
	public ResponseEntity<Void> resendHandWaving(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") Long meetingId,
		@Parameter(description = "손 흔들기 ID", example = "1", required = true) @PathVariable("handWavingId") Long handWavingId
	) {
		handWavingService.resendHandWaving(userId, meetingId, handWavingId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "모른척하기", description = "손을 흔들어 인사한 모임원에게 모른척 합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모른척하기 성공"),
		@ApiResponse(responseCode = "403", description = "USER_NOT_MEMBER_OF_MEETING"),
		@ApiResponse(responseCode = "404", description = "HAND_WAVING_NOT_FOUND"),
	})
	@PatchMapping("/{handWavingId}/ignore")
	public ResponseEntity<Void> ignoreHandWaving(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") Long meetingId,
		@Parameter(description = "손 흔들기 ID", example = "1", required = true) @PathVariable("handWavingId") Long handWavingId
	) {
		handWavingService.ignoreHandWaving(userId, meetingId, handWavingId);
		return ResponseEntity.ok().build();
	}
}
