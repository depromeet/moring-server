package org.depromeet.sambad.moring.meeting.question.application;

import java.util.Optional;

import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MeetingQuestionListResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MeetingQuestionResponse;
import org.springframework.data.domain.Pageable;

public interface MeetingQuestionRepository {

	void save(MeetingQuestion meetingQuestion);

	boolean existsByQuestion(Long meetingId, Long questionId);

	MeetingQuestionResponse findActiveOneByMeeting(Long meetingId, Long loginMeetingMemberId);

	MeetingQuestionListResponse findInactiveList(Long meetingId, Long loginMeetingMemberId, Pageable pageable);

	Optional<MeetingQuestion> findById(Long id);
}
