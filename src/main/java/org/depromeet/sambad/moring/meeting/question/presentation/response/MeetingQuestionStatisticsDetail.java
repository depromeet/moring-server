package org.depromeet.sambad.moring.meeting.question.presentation.response;

public record MeetingQuestionStatisticsDetail(
	int rank,
	String answer,
	int count,
	int percentage
) {
}
