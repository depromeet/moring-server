package org.depromeet.sambad.moring.meeting.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import org.depromeet.sambad.moring.common.response.PageableResponse;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.springframework.data.domain.Pageable;

import io.swagger.v3.oas.annotations.media.Schema;

public record FullInactiveMeetingQuestionListResponse<T>(
	@Schema(
		description = "비활성화된 모임 질문 목록",
		example = "[{\"id\":1,\"questionImageFileUrl\":\"https://avatars.githubusercontent.com/u/173370739?v=4\",\"title\":갖고 싶은 초능력은?,\"questionNumber\":\"18\",\"startDate\":\"2024-07-09\",\"targetMember\":{\"id\":1,\"name\":\"이한음\",\"profileImageFileUrl\":\"https://example.com\",\"role\":\"OWNER}]",
		requiredMode = REQUIRED
	)
	List<FullInactiveMeetingQuestionListResponseDetail> content,
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
