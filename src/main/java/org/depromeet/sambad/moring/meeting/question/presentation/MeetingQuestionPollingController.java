package org.depromeet.sambad.moring.meeting.question.presentation;

import java.util.Optional;

import org.depromeet.sambad.moring.meeting.question.application.MeetingQuestionPollingService;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MeetingQuestionPollingResponse;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "실시간 모임의 릴레이 질문", description = "실시간 알림을 위한 모임원이 선택한 릴레이 질문 관련 api / 담당자: 김나현")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/meetings/{meetingId}/questions")
public class MeetingQuestionPollingController {

	private final MeetingQuestionPollingService meetingQuestionPollingService;

	@Operation(summary = "새로운 릴레이 질문 등록 여부 조회",
		description = "- 모임의 <b>새로운 릴레이 질문 등록 여부</b> / <b>질문인 대상 여부</b> / <b>질문 등록 여부</b>를 확인합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "새로운 릴레이 질문이 등록된 경우 바디와 함께 200 응답합니다."),
		@ApiResponse(responseCode = "204", description = "새로운 릴레이 질문이 없다면 204 응답합니다.",
			content = @Content(schema = @Schema(implementation = Object.class))),
		@ApiResponse(responseCode = "404", description = "MEETING_MEMBER_NOT_FOUND")
	})
	@GetMapping("/{meetingQuestionId}/new")
	public ResponseEntity<MeetingQuestionPollingResponse> getNewMeetingQuestion(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") Long meetingId,
		@Parameter(description = "현재 모임 질문 ID", example = "1") @PathVariable(value = "meetingId", required = false) Long meetingQuestionId
	) {
		Optional<MeetingQuestionPollingResponse> newMeetingQuestion = meetingQuestionPollingService.getNewMeetingQuestion(
			userId, meetingId, meetingQuestionId);

		if (newMeetingQuestion.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(newMeetingQuestion.get());
	}
}

