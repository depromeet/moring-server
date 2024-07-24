package org.depromeet.sambad.moring.question.presentation;

import org.depromeet.sambad.moring.question.application.QuestionService;
import org.depromeet.sambad.moring.question.domain.Question;
import org.depromeet.sambad.moring.question.presentation.response.QuestionListResponse;
import org.depromeet.sambad.moring.question.presentation.response.QuestionResponse;
import org.depromeet.sambad.moring.user.presentation.resolver.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Tag(name = "릴레이 질문", description = "모임의 질문이 아닌, 운영자가 관리하는 릴레이 질문 관련 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class QuestionController {

	private final QuestionService questionService;

	@Operation(summary = "질문 리스트 내 질문 단건 조회")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			content = @Content(schema = @Schema(implementation = QuestionResponse.class))
		),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_QUESTION")
	})
	@GetMapping("/questions/{questionId}")
	public ResponseEntity<QuestionResponse> findQuestion(
		@PathVariable(value = "questionId") @Positive Long questionId
	) {
		Question question = questionService.getById(questionId);
		return ResponseEntity.ok().body(QuestionResponse.from(question));
	}

	@Operation(summary = "선택 가능한 질문 리스트 조회")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			content = @Content(schema = @Schema(implementation = QuestionListResponse.class)))
	})
	@GetMapping("/questions")
	public ResponseEntity<Object> findQuestions(
		@UserId Long userId,
		@RequestParam(value = "page", defaultValue = "0") @Positive int page,
		@RequestParam(value = "size", defaultValue = "10") @Positive int size
	) {
		QuestionListResponse response = questionService.findQuestions(userId, page, size);
		return ResponseEntity.ok().body(response);
	}
}