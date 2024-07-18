package org.depromeet.sambad.moring.meetingQuestion.application;

import org.depromeet.sambad.moring.meetingQuestion.domain.MeetingQuestion;

public interface MeetingQuestionRepository {

	void save(MeetingQuestion meetingQuestion);

	boolean existsByQuestion(Long meetingId, Long questionId);
}
