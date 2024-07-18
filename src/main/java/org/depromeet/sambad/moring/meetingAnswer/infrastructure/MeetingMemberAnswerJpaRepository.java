package org.depromeet.sambad.moring.meetingAnswer.infrastructure;

import org.depromeet.sambad.moring.meetingAnswer.domain.MeetingMemberAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingMemberAnswerJpaRepository extends JpaRepository<MeetingMemberAnswer, Long> {
}
