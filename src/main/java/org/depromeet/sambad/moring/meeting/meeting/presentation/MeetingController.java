package org.depromeet.sambad.moring.meeting.meeting.presentation;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.depromeet.sambad.moring.meeting.meeting.application.MeetingService;
import org.depromeet.sambad.moring.meeting.meeting.application.MeetingTypeService;
import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.meeting.domain.MeetingType;
import org.depromeet.sambad.moring.meeting.meeting.presentation.request.MeetingPersistRequest;
import org.depromeet.sambad.moring.meeting.meeting.presentation.response.MeetingPersistResponse;
import org.depromeet.sambad.moring.meeting.meeting.presentation.response.MeetingTypeResponse;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Meeting", description = "모임 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/meetings")
public class MeetingController {

	private final MeetingService meetingService;
	private final MeetingTypeService meetingTypeService;

	@Operation(summary = "모임 생성", description = "모임을 생성합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "모임 생성 성공"),
		@ApiResponse(responseCode = "400", description = "EXCEED_MAX_MEETING_COUNT: 최대 모임 개수 초과"),
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