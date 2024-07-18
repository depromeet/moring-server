package org.depromeet.sambad.moring.meeting.member.presentation;

import static org.springframework.http.HttpStatus.*;

import org.depromeet.sambad.moring.meeting.member.application.HobbyService;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.presentation.request.MeetingMemberPersistRequest;
import org.depromeet.sambad.moring.meeting.member.presentation.response.HobbyResponse;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberPersistResponse;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "MeetingMember", description = "모임 멤버 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/meetings/members")
public class MeetingMemberController {

	private final MeetingMemberService meetingMemberService;
	private final HobbyService hobbyService;

	@Operation(summary = "모임 가입", description = "특정 모임에 신규 모임 멤버를 등록합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "모임 멤버 등록 성공"),
		@ApiResponse(responseCode = "400", description = "EXCEED_MAX_MEETING_COUNT: 모임 최대 참여 및 개설 횟수를 초과했습니다."),
		@ApiResponse(responseCode = "404", description = "MEETING_NOT_FOUND: 코드에 해당하는 모임을 찾을 수 없습니다, USER_NOT_FOUND: 사용자를 찾을 수 없습니다"),
		@ApiResponse(responseCode = "409", description = "MEETING_MEMBER_ALREADY_EXISTS: 이미 모임 멤버로 등록된 사용자입니다.")
	})
	@PostMapping
	public ResponseEntity<MeetingMemberPersistResponse> createMeetingMember(
		@UserId Long userId,
		@Parameter(description = "모임의 고유 초대 코드", example = "A1G05C", required = true)@RequestParam String code,
		@Valid @RequestBody MeetingMemberPersistRequest request
	) {
		MeetingMemberPersistResponse response = meetingMemberService.registerMeetingMember(userId, code, request);

		return ResponseEntity.status(CREATED).body(response);
	}

	@Operation(summary = "모임원 취미 목록 조회", description = "모임원이 선택할 수 있는 취미 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "취미 목록 조회 성공")
	@GetMapping("/hobbies")
	public ResponseEntity<HobbyResponse> getHobbies() {
		return ResponseEntity.ok(hobbyService.getHobbies());
	}
}