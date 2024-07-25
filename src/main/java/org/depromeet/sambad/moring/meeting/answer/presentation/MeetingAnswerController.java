package org.depromeet.sambad.moring.meeting.answer.presentation;

import org.depromeet.sambad.moring.meeting.answer.application.MeetingAnswerService;
import org.depromeet.sambad.moring.meeting.answer.presentation.request.MeetingAnswerRequest;
import org.depromeet.sambad.moring.meeting.answer.presentation.response.MyMeetingAnswerListResponse;
import org.depromeet.sambad.moring.meeting.answer.presentation.response.SelectedAnswerResponse;
import org.depromeet.sambad.moring.meeting.answer.application.MeetingAnswerResultService;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Tag(name = "모임원의 답변", description = "모임 내 릴레이 질문에 대한 답변 api / 담당자 : 김나현")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MeetingAnswerController {

	private final MeetingAnswerService meetingAnswerService;
	private final MeetingAnswerResultService meetingAnswerResultService;

	@Operation(summary = "모임원 답변 등록")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "meetingQuestion(모임원 답변) 등록 성공"),
		@ApiResponse(responseCode = "404", description = "MEETING_MEMBER_NOT_FOUND / NOT_FOUND_MEETING_QUESTION / "
			+ "NOT_FOUND_ANSWER"),
		@ApiResponse(responseCode = "409", description = "DUPLICATE_MEETING_ANSWER / FINISHED_MEETING_QUESTION")
	})
	@PostMapping("/meetings/{meetingId}/questions/{meetingQuestionId}/answers")
	public ResponseEntity<Object> save(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") Long meetingId,
		@Parameter(description = "답변할 모임의 질문 ID", example = "1", required = true) @PathVariable("meetingQuestionId") @Positive Long meetingQuestionId,
		@Valid @RequestBody MeetingAnswerRequest request
	) {
		meetingAnswerService.save(userId, meetingId, meetingQuestionId, request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "내가 작성한 모든 질문의 답변 리스트 조회", description =
		"- 프로필 페이지 내 릴레이 질문 영역 조회 시 사용합니다.\n"
			+ "- 생성 순으로 오름차순 정렬하여 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "403", description = "USER_NOT_MEMBER_OF_MEETING")
	})
	@GetMapping("/meetings/{meetingId}/questions/answers/me")
	public ResponseEntity<MyMeetingAnswerListResponse> findMyList(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") Long meetingId
	) {
		MyMeetingAnswerListResponse response = meetingAnswerService.getMyList(userId, meetingId);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "가장 많이 선택된 답변 조회", description = "가장 많이 선택된 답변과 이를 선택한 모임원 리스트를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공"),
		@ApiResponse(responseCode = "403", description = "USER_NOT_MEMBER_OF_MEETING"),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_MEETING_QUESTION")
	})
	@GetMapping("/meetings/{meetingId}/questions/{questionId}/answers/most-selected")
	public ResponseEntity<SelectedAnswerResponse> getMostSelectedMeetingAnswer(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable Long meetingId,
		@Parameter(description = "모임 질문 ID", example = "1", required = true)
		@PathVariable(name = "questionId") Long meetingQuestionId
	) {
		SelectedAnswerResponse response = meetingAnswerResultService.getMostSelectedAnswer(
			userId, meetingId, meetingQuestionId);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "같은 답변을 선택한 모임원 리스트 조회", description = "같은 답변을 선택한 모임원 리스트를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공"),
		@ApiResponse(responseCode = "403", description = "USER_NOT_MEMBER_OF_MEETING"),
		@ApiResponse(responseCode = "404", description = "MEETING_MEMBER_NOT_FOUND")
	})
	@GetMapping("/meetings/{meetingId}/questions/{questionId}/answers/selected-same")
	public ResponseEntity<SelectedAnswerResponse> getSelectedSameMeetingAnswers(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable Long meetingId,
		@Parameter(description = "모임 질문 ID", example = "1", required = true)
		@PathVariable(name = "questionId") Long meetingQuestionId
	) {
		SelectedAnswerResponse response = meetingAnswerResultService.getSelectedSameAnswer(
			userId, meetingId, meetingQuestionId);

		return ResponseEntity.ok(response);
	}
}