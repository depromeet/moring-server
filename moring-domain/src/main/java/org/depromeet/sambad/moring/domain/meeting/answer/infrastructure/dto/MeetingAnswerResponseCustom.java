package org.depromeet.sambad.moring.domain.meeting.answer.infrastructure.dto;

import java.util.List;

import org.depromeet.sambad.moring.domain.answer.domain.Answer;
import org.depromeet.sambad.moring.domain.meeting.question.domain.MeetingQuestion;

import com.querydsl.core.annotations.QueryProjection;

public record MeetingAnswerResponseCustom(
	MeetingQuestion meetingQuestion,
	List<Answer> meetingAnswers,
	String comment
) {

	@QueryProjection
	public MeetingAnswerResponseCustom {
	}

	public List<String> getMeetingAnswers() {
		return meetingAnswers.stream()
			.map(Answer::getContent)
			.toList();
	}
}