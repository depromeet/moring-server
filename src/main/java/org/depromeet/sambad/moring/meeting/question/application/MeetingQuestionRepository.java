package org.depromeet.sambad.moring.meeting.question.application;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.response.ActiveMeetingQuestionResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.FullInactiveMeetingQuestionListResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MeetingQuestionStatisticsDetail;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MostInactiveMeetingQuestionListResponse;
import org.springframework.data.domain.Pageable;

public interface MeetingQuestionRepository {

	MeetingQuestion save(MeetingQuestion meetingQuestion);

	Optional<MeetingQuestion> findNextQuestion(Long meetingId);

	boolean existsByQuestion(Long meetingId, Long questionId);

	ActiveMeetingQuestionResponse findActiveOneByMeeting(Long meetingId, Long loginMeetingMemberId);

	Optional<MeetingQuestion> findActiveOneByMeeting(Long meetingId);

	Optional<MeetingQuestion> findRegisteredOneByMeeting(Long meetingId);

	MostInactiveMeetingQuestionListResponse findMostInactiveList(Long meetingId);

	FullInactiveMeetingQuestionListResponse findFullInactiveList(Long meetingId, Pageable pageable);

	Optional<MeetingQuestion> findByMeetingIdAndMeetingQuestionId(Long meetingId, Long meetingQuestionId);

	List<MeetingQuestionStatisticsDetail> findStatistics(Long meetingQuestionId);

	List<MeetingMember> findMeetingMembersByMeetingQuestionId(Long meetingQuestionId);
}
