package org.depromeet.sambad.moring.meeting.answer.infrastructure.dto;

import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.meeting.comment.domain.comment.MeetingQuestionComment;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

import com.querydsl.core.annotations.QueryProjection;

public record MyMeetingAnswerResponseCustom(
	MeetingQuestion meetingQuestion,
	MeetingAnswer meetingAnswer,
	MeetingQuestionComment comment
) {

	@QueryProjection
	public MyMeetingAnswerResponseCustom {
	}
}
