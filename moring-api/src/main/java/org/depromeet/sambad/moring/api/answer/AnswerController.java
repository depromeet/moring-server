package org.depromeet.sambad.moring.api.answer;

import org.depromeet.sambad.moring.domain.answer.application.AnswerService;
import org.depromeet.sambad.moring.domain.answer.presentation.request.AnswerRequest;
import org.springframework.http.ResponseEntity;
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

@Tag(name = "릴레이 질문 답변", description = "모임의 질문이 아닌, 운영자가 관리하는 릴레이 질문 답변 관련 api / 담당자 : 이한음")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/answers")
public class AnswerController {

	private final AnswerService answerService;

	@Operation(summary = "답변 추가", description = "새로운 답변을 등록하는 API 입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "답변 등록 성공")
	})
	@PostMapping("/answers")
	public ResponseEntity<Void> saveAnswer(
		@Valid @RequestBody AnswerRequest answerRequest
	) {
		answerService.saveAnswer(answerRequest);
		return ResponseEntity.ok().build();
	}
}
