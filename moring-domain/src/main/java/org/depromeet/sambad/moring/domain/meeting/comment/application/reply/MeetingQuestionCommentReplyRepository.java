package org.depromeet.sambad.moring.domain.meeting.comment.application.reply;

import java.util.Optional;

import org.depromeet.sambad.moring.domain.meeting.comment.domain.reply.MeetingQuestionCommentReply;

public interface MeetingQuestionCommentReplyRepository {
	void save(MeetingQuestionCommentReply commentReply);

	Optional<MeetingQuestionCommentReply> findById(Long meetingQuestionCommentReplyId);

	void delete(MeetingQuestionCommentReply commentReply);
}
