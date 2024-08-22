package org.depromeet.sambad.moring.meeting.answer.infrastructure;

import java.util.List;

import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingAnswerJpaRepository extends JpaRepository<MeetingAnswer, Long> {

	boolean existsByMeetingQuestionIdAndMeetingMemberId(Long meetingQuestionId, Long meetingMemberId);

	List<MeetingAnswer> findByMeetingQuestionIdAndMeetingMemberId(Long meetingQuestionId, Long meetingMemberId);

	List<MeetingAnswer> findAllByMeetingMemberIdAndMeetingQuestionIdNotIn(Long meetingMemberId,
		List<Long> activeMeetingQuestionIds);

	List<MeetingAnswer> findAllByMeetingMemberIdAndMeetingQuestionIdIn(Long meetingMemberId,
		List<Long> activeMeetingQuestionIds);
}