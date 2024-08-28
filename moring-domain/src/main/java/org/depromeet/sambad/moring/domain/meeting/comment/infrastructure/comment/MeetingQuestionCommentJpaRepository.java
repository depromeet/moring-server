package org.depromeet.sambad.moring.domain.meeting.comment.infrastructure.comment;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.domain.meeting.comment.domain.comment.MeetingQuestionComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingQuestionCommentJpaRepository extends JpaRepository<MeetingQuestionComment, Long> {
	List<MeetingQuestionComment> findAllByMeetingQuestionId(Long meetingQuestionId);

	Optional<MeetingQuestionComment> findByIdAndMeetingQuestionId(Long id, Long meetingQuestionId);
}
