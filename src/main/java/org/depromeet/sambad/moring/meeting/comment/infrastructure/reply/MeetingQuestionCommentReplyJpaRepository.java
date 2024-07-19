package org.depromeet.sambad.moring.meeting.comment.infrastructure.reply;

import org.depromeet.sambad.moring.meeting.comment.domain.reply.MeetingQuestionCommentReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingQuestionCommentReplyJpaRepository extends JpaRepository<MeetingQuestionCommentReply, Long> {
}
