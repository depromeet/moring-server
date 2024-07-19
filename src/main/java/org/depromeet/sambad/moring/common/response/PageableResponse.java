package org.depromeet.sambad.moring.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record PageableResponse(
	@Schema(defaultValue = "0", description = "페이지 인덱스로, 0 부터 시작합니다. 별도의 값 없이 요청 시, 0 으로 설정됩니다.")
	int page,

	@Schema(defaultValue = "10", description = "페이지 내 최대 응답 개수입니다. 별도의 값 없이 요청 시, 10 으로 설정됩니다.")
	int size,

	@Schema(defaultValue = "3", description = "전체 페이지 수입니다.")
	int totalPages,

	@Schema(defaultValue = "false", description = "현재 응답하는 페이지가 마지막 일 시, true 로 설정됩니다.")
	boolean isEnd
) {
}