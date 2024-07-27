package org.depromeet.sambad.moring.common.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.springframework.data.domain.Pageable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record PageableResponse<T>(
	@Schema(defaultValue = "0", description = "페이지 인덱스로, 0 부터 시작합니다. 별도의 값 없이 요청 시, 0 으로 설정됩니다.",
		requiredMode = REQUIRED)
	int page,

	@Schema(defaultValue = "10", description = "페이지 내 최대 응답 개수입니다. 별도의 값 없이 요청 시, 10 으로 설정됩니다.",
		requiredMode = REQUIRED)
	int size,

	@Schema(defaultValue = "3", description = "전체 페이지 수입니다.", requiredMode = REQUIRED)
	int totalPages,

	@Schema(defaultValue = "false", description = "현재 응답하는 페이지가 마지막 일 시, true 로 설정됩니다.", requiredMode = REQUIRED)
	boolean isEnd
) {

	public static <T> PageableResponse<T> of(Pageable pageable, List<T> totalElements) {
		int totalPageSize = (int)Math.ceil((double)totalElements.size() / pageable.getPageSize());
		boolean isEnd = pageable.getPageNumber() + 1 >= totalPageSize;
		return PageableResponse.<T>builder()
			.page(pageable.getPageNumber())
			.size(pageable.getPageSize())
			.totalPages(totalPageSize)
			.isEnd(isEnd)
			.build();
	}
}
