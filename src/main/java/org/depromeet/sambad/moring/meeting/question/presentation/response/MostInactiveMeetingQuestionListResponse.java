package org.depromeet.sambad.moring.meeting.question.presentation.response;

import java.util.List;

public record MostInactiveMeetingQuestionListResponse(
	List<MostInactiveMeetingQuestionListResponseDetail> content
) {

	public static MostInactiveMeetingQuestionListResponse from(
		List<MostInactiveMeetingQuestionListResponseDetail> responseDetails
	) {
		return new MostInactiveMeetingQuestionListResponse(responseDetails);
	}
}
