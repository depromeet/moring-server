package org.depromeet.sambad.moring.meeting.comment.application.reply;

import org.depromeet.sambad.moring.meeting.comment.domain.reply.MeetingQuestionCommentReply;

public interface MeetingQuestionCommentReplyRepository {
	void save(MeetingQuestionCommentReply commentReply);
}
