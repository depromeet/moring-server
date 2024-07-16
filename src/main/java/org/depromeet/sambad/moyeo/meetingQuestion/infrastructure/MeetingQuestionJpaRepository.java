package org.depromeet.sambad.moyeo.meetingQuestion.infrastructure;

import org.depromeet.sambad.moyeo.meetingQuestion.domain.MeetingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingQuestionJpaRepository extends JpaRepository<MeetingQuestion, Long> {
}
