package org.depromeet.sambad.moring.meeting.question.presentation.response;

import java.util.List;

public record MeetingQuestionStatisticsResponse(
	List<MeetingQuestionStatisticsDetail> contents
) {
	public static MeetingQuestionStatisticsResponse of(List<MeetingQuestionStatisticsDetail> statistics) {
		return new MeetingQuestionStatisticsResponse(statistics);
	}
}
