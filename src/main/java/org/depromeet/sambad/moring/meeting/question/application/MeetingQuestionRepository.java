package org.depromeet.sambad.moring.meeting.question.application;

import java.util.Optional;

import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.response.ActiveMeetingQuestionResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.FullInactiveMeetingQuestionListResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MostInactiveMeetingQuestionListResponse;
import org.springframework.data.domain.Pageable;

public interface MeetingQuestionRepository {

	void save(MeetingQuestion meetingQuestion);

	boolean existsByQuestion(Long meetingId, Long questionId);

	ActiveMeetingQuestionResponse findActiveOneByMeeting(Long meetingId, Long loginMeetingMemberId);

	MostInactiveMeetingQuestionListResponse findMostInactiveList(Long meetingId, Long loginMeetingMemberId);

	FullInactiveMeetingQuestionListResponse findFullInactiveList(Long meetingId, Long loginMeetingMemberId,
		Pageable pageable);

	Optional<MeetingQuestion> findByMeetingIdAndMeetingQuestionId(Long meetingId, Long meetingQuestionId);
}
