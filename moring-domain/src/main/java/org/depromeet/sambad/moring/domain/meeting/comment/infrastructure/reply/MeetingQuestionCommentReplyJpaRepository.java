package org.depromeet.sambad.moring.domain.meeting.comment.infrastructure.reply;

import java.util.Optional;

import org.depromeet.sambad.moring.domain.meeting.comment.domain.reply.MeetingQuestionCommentReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingQuestionCommentReplyJpaRepository extends JpaRepository<MeetingQuestionCommentReply, Long> {

	Optional<MeetingQuestionCommentReply> findByMeetingQuestionCommentIdAndId(Long parentCommentId, Long id);
}
