package org.depromeet.sambad.moring.meeting.question.presentation;

import org.depromeet.sambad.moring.meeting.question.application.MeetingQuestionService;
import org.depromeet.sambad.moring.meeting.question.presentation.request.MeetingQuestionRequest;
import org.depromeet.sambad.moring.meeting.question.presentation.response.ActiveMeetingQuestionResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.FullInactiveMeetingQuestionListResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MostInactiveMeetingQuestionListResponse;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Tag(name = "모임의 릴레이 질문", description = "모임원이 선택한 릴레이 질문 관련 api / 담당자: 김나현")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MeetingQuestionController {

	private final MeetingQuestionService meetingQuestionService;

	@Operation(summary = "다음 릴레이 질문 저장", description = "모임의 다음 릴레이 질문과 질문인을 저장합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201"),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_QUESTION"),
		@ApiResponse(responseCode = "409", description = "DUPLICATE_MEETING_QUESTION / INVALID_MEETING_MEMBER_TARGET")
	})
	@PostMapping("/meetings/{meetingId}/questions")
	public ResponseEntity<Object> save(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") Long meetingId,
		@Valid @RequestBody MeetingQuestionRequest request
	) {
		meetingQuestionService.save(userId, meetingId, request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "현재 진행 중인 릴레이 질문 조회")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "진행 중인 릴레이 질문이 없다면, 200 OK 코드와 body 는 null 을 반환합니다."
		)
	})
	@GetMapping("/meetings/{meetingId}/questions/active")
	public ResponseEntity<ActiveMeetingQuestionResponse> findActiveOne(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") Long meetingId
	) {
		ActiveMeetingQuestionResponse activeOne = meetingQuestionService.findActiveOne(userId, meetingId);
		return ResponseEntity.ok(activeOne);
	}

	@Operation(summary = "홈 화면 내 종료된 릴레이 질문 2건 조회", description = "- 참여율 순으로 내림차순 정렬한 후 2건 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "404", description = "USER_NOT_MEMBER_OF_MEETING")
	})
	@GetMapping("/meetings/{meetingId}/questions/inactive/top")
	public ResponseEntity<MostInactiveMeetingQuestionListResponse> findMostInactiveList(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") Long meetingId
	) {
		MostInactiveMeetingQuestionListResponse inactiveList = meetingQuestionService.findMostInactiveList(userId,
			meetingId);
		return ResponseEntity.ok(inactiveList);
	}

	@Operation(summary = "전체 종료된 릴레이 질문 리스트 조회", description = "- 페이징 적용 API 로, page는 0부터 시작합니다.\n"
		+ "- 참여율 순으로 내림차순 정렬하여 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "404", description = "USER_NOT_MEMBER_OF_MEETING")
	})
	@GetMapping("/meetings/{meetingId}/questions/inactive")
	public ResponseEntity<FullInactiveMeetingQuestionListResponse> findFullInactiveList(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") Long meetingId,
		@Parameter(description = "페이지 인덱스, 요청 값이 없으면 0으로 설정", example = "0") @RequestParam(value = "page", defaultValue = "0") @Positive int page,
		@Parameter(description = "응답 개수, 요청 값이 없으면 10으로 설정", example = "10") @RequestParam(value = "size", defaultValue = "10") @Positive int size
	) {
		FullInactiveMeetingQuestionListResponse inactiveList = meetingQuestionService.findFullInactiveList(userId,
			meetingId,
			PageRequest.of(page, size));
		return ResponseEntity.ok(inactiveList);
	}

	@Operation(summary = "내가 작성한 모든 질문의 답변 리스트 조회", description =
		"- 자기 소개 페이지 내 유저가 작성한 모든 릴레이 질문의 답변 목록을 조회 시 사용합니다.\n"
			+ "- 생성 순으로 오름차순 정렬하여 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "404", description = "USER_NOT_MEMBER_OF_MEETING")
	})
	@GetMapping("/meetings/{meetingId}/questions")
	public ResponseEntity<Object> findMyList(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") Long meetingId
	) {
		// TODO: 구현
		return ResponseEntity.ok().build();
	}
}
