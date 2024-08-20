package org.depromeet.sambad.moring.user.presentation;

import org.depromeet.sambad.moring.user.application.UserService;
import org.depromeet.sambad.moring.user.domain.User;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.depromeet.sambad.moring.user.presentation.response.OnboardingResponse;
import org.depromeet.sambad.moring.user.presentation.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
}
