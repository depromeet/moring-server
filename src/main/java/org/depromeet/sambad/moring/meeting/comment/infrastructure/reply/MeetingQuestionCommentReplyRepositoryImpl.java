package org.depromeet.sambad.moring.meeting.comment.infrastructure.reply;

import org.depromeet.sambad.moring.meeting.comment.application.reply.MeetingQuestionCommentReplyRepository;
import org.depromeet.sambad.moring.meeting.comment.domain.reply.MeetingQuestionCommentReply;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MeetingQuestionCommentReplyRepositoryImpl implements MeetingQuestionCommentReplyRepository {
	private final MeetingQuestionCommentReplyJpaRepository meetingQuestionCommentReplyJpaRepository;

	@Override
	public void save(MeetingQuestionCommentReply commentReply) {
		meetingQuestionCommentReplyJpaRepository.save(commentReply);
	}
}
