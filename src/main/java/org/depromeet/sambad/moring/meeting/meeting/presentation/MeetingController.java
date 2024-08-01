package org.depromeet.sambad.moring.meeting.meeting.presentation;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.depromeet.sambad.moring.meeting.meeting.application.MeetingService;
import org.depromeet.sambad.moring.meeting.meeting.application.MeetingTypeService;
import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.meeting.domain.MeetingType;
import org.depromeet.sambad.moring.meeting.meeting.presentation.request.MeetingPersistRequest;
import org.depromeet.sambad.moring.meeting.meeting.presentation.response.MeetingPersistResponse;
import org.depromeet.sambad.moring.meeting.meeting.presentation.response.MeetingResponse;
import org.depromeet.sambad.moring.meeting.meeting.presentation.response.MeetingNameResponse;
import org.depromeet.sambad.moring.meeting.meeting.presentation.response.MeetingTypeResponse;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "모임", description = "모임 관련 API / 담당자 : 권기준")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/meetings")
public class MeetingController {

	private final MeetingService meetingService;
	private final MeetingTypeService meetingTypeService;

	@Operation(summary = "모임 조회", description = "가입되어 있는 모임 목록을 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 조회 성공"),
	})
	@GetMapping
	public ResponseEntity<MeetingResponse> getMeetings(@UserId Long userId) {
		MeetingResponse response = meetingService.getMeetingResponse(userId);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "초대 코드 기반 모임명 조회", description = "초대 코드를 기반으로 모임명을 조회하며, 모임 존재 여부를 검증합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임명 조회 성공"),
		@ApiResponse(responseCode = "404", description = "MEETING_NOT_FOUND"),
	})
	@GetMapping("/name")
	public ResponseEntity<MeetingNameResponse> getMeetingName(@RequestParam String code) {
		MeetingNameResponse response = meetingService.getMeetingNameByCode(code);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "모임 생성", description = "모임을 생성합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "모임 생성 성공"),
		@ApiResponse(responseCode = "400", description = "EXCEED_MAX_MEETING_COUNT"),
	})
	@PostMapping
	public ResponseEntity<MeetingPersistResponse> createMeeting(
		@UserId Long userId,
		@Valid @RequestBody MeetingPersistRequest request
	) {
		Meeting meeting = meetingService.createMeeting(userId, request);

		return ResponseEntity.status(CREATED)
			.body(MeetingPersistResponse.from(meeting));
	}

	@Operation(summary = "모임 유형 조회", description = "모임 유형 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "모임 유형 조회 성공")
	@GetMapping("/types")
	public ResponseEntity<MeetingTypeResponse> getMeetingTypes() {
		List<MeetingType> meetingTypes = meetingTypeService.getMeetingTypes();

		return ResponseEntity.ok(MeetingTypeResponse.from(meetingTypes));
	}
}
