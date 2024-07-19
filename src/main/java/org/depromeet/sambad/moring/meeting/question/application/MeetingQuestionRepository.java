package org.depromeet.sambad.moring.meeting.question.application;

import java.util.Optional;

import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MeetingQuestionResponse;

public interface MeetingQuestionRepository {

	void save(MeetingQuestion meetingQuestion);

	boolean existsByQuestion(Long meetingId, Long questionId);

	MeetingQuestionResponse findActiveOneByMeeting(Long meetingId, Long loginMeetingMemberId);

	Optional<MeetingQuestion> findById(Long id);
}
