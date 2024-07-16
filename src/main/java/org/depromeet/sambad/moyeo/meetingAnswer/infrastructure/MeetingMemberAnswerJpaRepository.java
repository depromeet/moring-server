package org.depromeet.sambad.moyeo.meetingAnswer.infrastructure;

import org.depromeet.sambad.moyeo.meetingAnswer.domain.MeetingMemberAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingMemberAnswerJpaRepository extends JpaRepository<MeetingMemberAnswer, Long> {
}
