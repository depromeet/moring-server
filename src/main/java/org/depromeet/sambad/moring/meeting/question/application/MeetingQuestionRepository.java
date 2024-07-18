package org.depromeet.sambad.moring.meeting.question.application;

import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

public interface MeetingQuestionRepository {

	void save(MeetingQuestion meetingQuestion);

	boolean existsByQuestion(Long meetingId, Long questionId);
}
