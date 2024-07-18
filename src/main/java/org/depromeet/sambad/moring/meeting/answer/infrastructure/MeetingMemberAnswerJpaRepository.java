package org.depromeet.sambad.moring.meeting.answer.infrastructure;

import org.depromeet.sambad.moring.meeting.answer.domain.MeetingMemberAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingMemberAnswerJpaRepository extends JpaRepository<MeetingMemberAnswer, Long> {
}
