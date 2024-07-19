package org.depromeet.sambad.moring.meeting.answer.infrastructure;

import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingAnswerJpaRepository extends JpaRepository<MeetingAnswer, Long> {

	boolean existsByMeetingQuestionIdAndMeetingMemberId(Long meetingQuestionId, Long meetingMemberId);
}
