package org.depromeet.sambad.moring.meeting.question.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingQuestionJpaRepository extends JpaRepository<MeetingQuestion, Long> {

	boolean existsByMeetingIdAndQuestionId(Long meetingId, Long questionId);

	Optional<MeetingQuestion> findByMeetingIdAndId(Long meetingId, Long meetingQuestionId);

	List<MeetingQuestion> findAllByStatusAndExpiredAtBefore(MeetingQuestionStatus status, LocalDateTime now);

	Optional<MeetingQuestion> findFirstByMeetingIdAndStatusOrderByStartTime(Long meetingId,
		MeetingQuestionStatus status);

	Optional<MeetingQuestion> findFirstByMeetingIdAndStatusAndStartTimeAfterOrderByStartTime(
		Long meetingId, MeetingQuestionStatus status, LocalDateTime now);

	Optional<MeetingQuestion> findFirstByMeetingIdAndStatusAndQuestionIsNotNullOrderByStartTime(Long meetingId,
		MeetingQuestionStatus status);
}
