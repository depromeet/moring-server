package org.depromeet.sambad.moring.user.presentation;

import org.depromeet.sambad.moring.user.application.UserService;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.depromeet.sambad.moring.user.presentation.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저", description = "유저 인증정보 관리 api / 담당자 : 권기준")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	/**
	 * TODO: API 스펙 확정되면 스웨거 추가 필요
	 */
	@GetMapping("/me")
	public ResponseEntity<UserResponse> getUser(@UserId Long userId) {
		UserResponse response = userService.findByUserId(userId);
		return ResponseEntity.ok(response);
	}
}
