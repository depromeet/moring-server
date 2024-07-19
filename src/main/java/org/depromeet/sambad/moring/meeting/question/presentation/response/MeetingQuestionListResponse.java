package org.depromeet.sambad.moring.meeting.question.presentation.response;

import java.util.List;

import org.depromeet.sambad.moring.common.response.PageableResponse;

public record MeetingQuestionListResponse<T>(
	int totalMeetingMemberCount,
	List<MeetingQuestionResponse> content,
	PageableResponse<T> pageable
) {

	public static <T> MeetingQuestionListResponse<T> of(
		int totalMeetingMemberCount,
		List<MeetingQuestionResponse> meetingQuestionResponses,
		PageableResponse<T> pageable
	) {
		return new MeetingQuestionListResponse<>(totalMeetingMemberCount, meetingQuestionResponses, pageable);
	}
}
