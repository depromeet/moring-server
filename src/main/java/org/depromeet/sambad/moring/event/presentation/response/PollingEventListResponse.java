package org.depromeet.sambad.moring.event.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import org.depromeet.sambad.moring.event.domain.Event;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.media.Schema;

public record PollingEventListResponse(
	@Schema(
		description = "새로운 턴의 릴레이 질문에서, 질문인이 질문을 등록했는지 여부와 질문인이 로그인한 유저인지 여부인지 응답합니다.",
		examples = "[{\"eventId\":1,\"userId\":1,\"eventType\":\"QUESTION_REGISTERED\"}]",
		requiredMode = REQUIRED
	)
	List<PollingEventListResponseDetail> contents
) {
	public static PollingEventListResponse from(List<Event> events) {
		List<PollingEventListResponseDetail> eventResponses = events.stream()
			.map(PollingEventListResponseDetail::from)
			.toList();
		return new PollingEventListResponse(eventResponses);
	}

	public ResponseEntity<PollingEventListResponse> toResponseEntity() {
		return contents.isEmpty()
			? ResponseEntity.noContent().build()
			: ResponseEntity.ok(this);
	}
}