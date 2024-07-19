package org.depromeet.sambad.moring.meeting.member.presentation;

import org.depromeet.sambad.moring.meeting.member.application.HobbyService;
import org.depromeet.sambad.moring.meeting.member.presentation.response.HobbyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class HobbyController {

	private final HobbyService hobbyService;

	@Operation(summary = "취미 목록 조회", description = "모임원이 선택할 수 있는 취미 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "취미 목록 조회 성공")
	@GetMapping("/hobbies")
	public ResponseEntity<HobbyResponse> getHobbies() {
		return ResponseEntity.ok(hobbyService.getHobbies());
	}
}