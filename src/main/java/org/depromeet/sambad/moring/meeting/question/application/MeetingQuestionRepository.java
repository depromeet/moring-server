package org.depromeet.sambad.moring.meeting.question.application;

import java.util.Optional;

import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.response.ActiveMeetingQuestionResponse;

public interface MeetingQuestionRepository {

	void save(MeetingQuestion meetingQuestion);

	boolean existsByQuestion(Long meetingId, Long questionId);

	ActiveMeetingQuestionResponse findActiveOneByMeeting(Long meetingId, Long loginMeetingMemberId);

	Optional<MeetingQuestion> findById(Long id);
}
