package org.depromeet.sambad.moring.question.presentation;

import org.depromeet.sambad.moring.question.application.QuestionService;
import org.depromeet.sambad.moring.question.domain.Question;
import org.depromeet.sambad.moring.question.presentation.response.QuestionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Tag(name = "Question", description = "질문 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class QuestionController {

	private final QuestionService questionService;

	@Operation(summary = "질문리스트 내 질문 단건 조회")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			content = @Content(schema = @Schema(implementation = QuestionResponse.class))
		),
		@ApiResponse(responseCode = "404", description = "NOT_FOUND_QUESTION")
	})
	@GetMapping("/questions/{question-id}")
	public ResponseEntity<QuestionResponse> findQuestion(
		@PathVariable(value = "question-id") @Positive Long questionId
	) {
		Question question = questionService.getById(questionId);
		return ResponseEntity.ok().body(QuestionResponse.from(question));
	}
}