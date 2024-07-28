package org.depromeet.sambad.moring.meeting.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.common.response.PageableResponse;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.springframework.data.domain.Pageable;

import io.swagger.v3.oas.annotations.media.Schema;

public record FullInactiveMeetingQuestionListResponse<T>(
	@Schema(
		description = "비활성화된 모임 질문 목록",
		requiredMode = REQUIRED
	)
	List<FullInactiveMeetingQuestionListResponseDetail> content,

	@Schema(description = "페이징 정보", requiredMode = REQUIRED)
	PageableResponse<T> pageable
) {

	public static FullInactiveMeetingQuestionListResponse of(List<MeetingQuestion> meetingQuestions,
		Pageable pageable) {
		List<FullInactiveMeetingQuestionListResponseDetail> responseDetails = FullInactiveMeetingQuestionListResponseDetail.from(
			meetingQuestions);

		return new FullInactiveMeetingQuestionListResponse(responseDetails,
			PageableResponse.of(pageable, responseDetails));
	}
}
