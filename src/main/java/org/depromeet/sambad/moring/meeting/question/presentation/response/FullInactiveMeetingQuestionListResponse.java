package org.depromeet.sambad.moring.meeting.question.presentation.response;

import java.util.List;

import org.depromeet.sambad.moring.common.response.PageableResponse;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.springframework.data.domain.Pageable;

public record FullInactiveMeetingQuestionListResponse<T>(
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
