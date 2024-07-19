package org.depromeet.sambad.moring.meeting.comment.infrastructure.comment;

import java.util.List;

import org.depromeet.sambad.moring.meeting.comment.domain.comment.MeetingQuestionComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingQuestionCommentJpaRepository extends JpaRepository<MeetingQuestionComment, Long> {
	List<MeetingQuestionComment> findAllByMeetingQuestionId(Long meetingQuestionId);
}
