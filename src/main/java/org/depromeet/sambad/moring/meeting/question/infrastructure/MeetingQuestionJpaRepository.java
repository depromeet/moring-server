package org.depromeet.sambad.moring.meeting.question.infrastructure;

import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingQuestionJpaRepository extends JpaRepository<MeetingQuestion, Long> {

	boolean existsByMeetingIdAndQuestionId(Long meetingId, Long questionId);
}
