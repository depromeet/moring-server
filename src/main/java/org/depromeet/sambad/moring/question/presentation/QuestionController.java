package org.depromeet.sambad.moring.question.presentation;

import java.util.List;

import org.depromeet.sambad.moring.question.application.QuestionService;
import org.depromeet.sambad.moring.question.domain.Question;
import org.depromeet.sambad.moring.question.presentation.request.QuestionRequest;
import org.depromeet.sambad.moring.question.presentation.response.QuestionListResponse;
import org.depromeet.sambad.moring.question.presentation.response.QuestionResponse;
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
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Tag(name = "릴레이 질문", description = "모임의 질문이 아닌, 운영자가 관리하는 릴레이 질문 관련 api / 담당자 : 김나현")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class QuestionController {

	private final QuestionService questionService;

	@Operation(summary = "질문 리스트 내 질문 단건 조회",
		description = "- 모임원이 질문을 선정할 때, 질문 리스트에서 질문을 클릭 시 사용하는 API 입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_QUESTION")
	})
	@GetMapping("/questions/{questionId}")
	public ResponseEntity<QuestionResponse> findQuestion(
		@Parameter(description = "질문 ID", example = "1", required = true) @PathVariable(value = "questionId") @Positive Long questionId
	) {
		Question question = questionService.getById(questionId);
		return ResponseEntity.ok().body(QuestionResponse.from(question));
	}

	@Operation(summary = "선택 가능한 질문 리스트 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "404", description = "MEETING_MEMBER_NOT_FOUND")
	})
	@GetMapping("/meetings/{meetingId}/questions")
	public ResponseEntity<QuestionListResponse> findQuestions(
		@UserId Long userId,
		@Parameter(description = "모임 ID", example = "1", required = true) @PathVariable("meetingId") Long meetingId,
		@Parameter(description = "페이지 인덱스, 요청 값이 없으면 0으로 설정", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
		@Parameter(description = "응답 개수, 요청 값이 없으면 10으로 설정", example = "10") @RequestParam(value = "size", defaultValue = "10") int size
	) {
		QuestionListResponse response = questionService.findQuestions(userId, meetingId, page, size);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "랜덤 질문 조회", description = "모임원이 질문을 선정할 때, 랜덤으로 질문을 추천해주는 API 입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "랜덤 질문 조회 성공"),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_AVAILABLE_QUESTION")
	})
	@GetMapping("/questions/random")
	public ResponseEntity<QuestionResponse> findRandomOne(
		@Parameter(description = "제외할 질문 ID 리스트", example = "1,2,3") @RequestParam("excludeQuestionIds") List<Long> excludeQuestionIds
	) {
		QuestionResponse randomOne = questionService.getRandomOne(excludeQuestionIds);
		return ResponseEntity.ok(randomOne);
	}

	@Operation(summary = "질문 추가", description = "새로운 질문을 등록하는 API 입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "질문 추가 등록성공")
	})
	@PostMapping("/questions")
	public ResponseEntity<Void> addQuestion(
		@Valid @RequestBody QuestionRequest questionRequest
	) {
		questionService.saveQuestion(questionRequest);
		return ResponseEntity.ok().build();
	}

}