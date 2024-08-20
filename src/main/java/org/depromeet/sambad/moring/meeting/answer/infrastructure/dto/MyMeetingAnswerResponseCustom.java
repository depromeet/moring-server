package org.depromeet.sambad.moring.meeting.answer.infrastructure.dto;

import java.util.List;

import org.depromeet.sambad.moring.answer.domain.Answer;

import com.querydsl.core.annotations.QueryProjection;

public record MyMeetingAnswerResponseCustom(
	Long meetingQuestionId,
	String meetingQuestionTitle,
	List<Answer> meetingAnswers,
	String comment,
	Boolean isHidden
) {

	@QueryProjection
	public MyMeetingAnswerResponseCustom {
	}

	public List<String> getMeetingAnswers() {
		return meetingAnswers.stream()
			.map(Answer::getContent)
			.toList();
	}
}
