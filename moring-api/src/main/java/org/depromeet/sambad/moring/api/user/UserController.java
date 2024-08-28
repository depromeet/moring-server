package org.depromeet.sambad.moring.api.user;

import org.depromeet.sambad.moring.domain.user.application.UserService;
import org.depromeet.sambad.moring.domain.user.presentation.request.LastMeetingRequest;
import org.depromeet.sambad.moring.domain.user.presentation.response.OnboardingResponse;
import org.depromeet.sambad.moring.domain.user.presentation.response.UserResponse;
import org.depromeet.sambad.moring.infra.annotation.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저", description = "유저 인증정보 관리 api / 담당자 : 권기준")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {

	private final UserService userService;

	@Operation(
		summary = "내 정보 조회",
		description = "현재 로그인 중인 유저의 정보를 조회합니다. 모임원 정보가 아닌 유저 정보로, 모임원 정보 설정 시 초기값 등으로 활용해주시면 됩니다."
	)
	@ApiResponse(responseCode = "200", description = "성공")
	@GetMapping("/me")
	public ResponseEntity<UserResponse> getUser(@UserId Long userId) {
		UserResponse response = userService.findByUserId(userId);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "토큰 유효성 조회", description = "오직 토큰의 유효성을 검증하기 위한 validation API 입니다.")
	@ApiResponse(responseCode = "202", description = "성공")
	@ApiResponse(responseCode = "401", description = "AUTHENTICATION_REQUIRED")
	@GetMapping("/validate-token")
	public ResponseEntity<Void> validateToken() {
		return ResponseEntity.accepted().build();
	}

	@Operation(
		summary = "온보딩 완료",
		description = "온보딩을 완료함으로써, 더 이상 온보딩 페이지로 이동하지 않도록 수정합니다."
	)
	@ApiResponse(responseCode = "200", description = "성공")
	@PatchMapping("/onboarding/complete")
	public ResponseEntity<OnboardingResponse> completeOnboarding(@UserId Long userId) {
		OnboardingResponse response = userService.completeOnboarding(userId);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "가장 최근 접속 모임 수정", description = "가장 최근 접속한 모임 정보를 수정합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공"),
		@ApiResponse(responseCode = "403", description = "USER_NOT_MEMBER_OF_MEETING")
	})
	@PatchMapping("/last-meeting")
	public ResponseEntity<Void> updateLastMeeting(@UserId Long userId, @RequestBody LastMeetingRequest request) {
		userService.updateLastMeeting(userId, request.meetingId());
		return ResponseEntity.ok().build();
	}
}
