package org.depromeet.sambad.moring.meeting.member.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.presentation.request.MeetingMemberPersistRequest;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberListResponse;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberPersistResponse;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberResponse;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "모임원", description = "모임 멤버 관련 api / 담당자 : 권기준")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/meetings/{meetingId}/members")
public class MeetingMemberController {

	private final MeetingMemberService meetingMemberService;

	@Operation(summary = "모임원 정보 조회", description = "특정 모임의 특정 모임원을 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임원 조회 성공"),
		@ApiResponse(responseCode = "403", description = "USER_NOT_MEMBER_OF_MEETING"),
		@ApiResponse(responseCode = "404", description = "MEETING_MEMBER_NOT_FOUND")
	})
	@GetMapping("/{memberId}")
	public ResponseEntity<MeetingMemberResponse> getMeetingMember(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") Long meetingId,
		@Parameter(description = "모임원 ID", example = "1", required = true) @PathVariable("memberId") Long memberId
	) {
		MeetingMemberResponse response = meetingMemberService.getMeetingMember(userId, meetingId, memberId);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "모임원 정보 조회", description = "특정 모임의 특정 모임원을 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임원 조회 성공"),
		@ApiResponse(responseCode = "403", description = "USER_NOT_MEMBER_OF_MEETING"),
		@ApiResponse(responseCode = "404", description = "MEETING_MEMBER_NOT_FOUND")
	})
	@GetMapping("/me")
	public ResponseEntity<MeetingMemberResponse> getMyMeetingMember(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") Long meetingId
	) {
		MeetingMemberResponse response = meetingMemberService.getMyMeetingMember(userId, meetingId);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "모임원 목록 조회", description = "특정 모임의 모임원 목록을 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임원 목록 조회 성공"),
		@ApiResponse(responseCode = "403", description = "USER_NOT_MEMBER_OF_MEETING")
	})
	@GetMapping
	public ResponseEntity<MeetingMemberListResponse> getMeetingMembers(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") Long meetingId
	) {
		MeetingMemberListResponse response = meetingMemberService.getMeetingMembers(userId, meetingId);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "모임 가입", description = "특정 모임에 신규 모임 멤버를 등록합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "모임 멤버 등록 성공"),
		@ApiResponse(responseCode = "400", description = "EXCEED_MAX_MEETING_COUNT / EXCEED_MAX_OWNER_COUNT / EXCEED_MAX_MEMBER_COUNT"),
		@ApiResponse(responseCode = "404", description = "MEETING_NOT_FOUND / USER_NOT_FOUND"),
		@ApiResponse(responseCode = "409", description = "MEETING_MEMBER_ALREADY_EXISTS")
	})
	@PostMapping
	public ResponseEntity<MeetingMemberPersistResponse> createMeetingMember(
		@UserId Long userId,
		@Parameter(description = "모임의 고유 초대 코드", example = "A1G05C", required = true) @RequestParam("code") String code,
		@Valid @RequestBody MeetingMemberPersistRequest request
	) {
		MeetingMemberPersistResponse response = meetingMemberService.registerMeetingMember(userId, code, request);
		return ResponseEntity.status(CREATED).body(response);
	}

	@Operation(summary = "다음 랜덤 질문 대상자 조회", description = "다음 릴레이 질문 랜덤 대상자를 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "랜덤 질문 대상자 조회 성공"),
		@ApiResponse(responseCode = "403", description = "USER_NOT_MEMBER_OF_MEETING"),
		@ApiResponse(responseCode = "404", description = "NO_MEETING_MEMBER_IN_CONDITION")
	})
	@GetMapping("/questions/target")
	public ResponseEntity<MeetingMemberResponse> getRandomQuestionMember(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") Long meetingId,
		@Parameter(description = "랜덤 대상자 제외 ID", example = "2") @RequestParam("excludeMemberIds") List<Long> excludeMemberIds
	) {
		MeetingMemberResponse response = meetingMemberService.getRandomMeetingMember(userId, meetingId, excludeMemberIds);
		return ResponseEntity.ok(response);
	}
}
