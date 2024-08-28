package org.depromeet.sambad.moring.domain.meeting.comment.infrastructure.reply;

import java.util.Optional;

import org.depromeet.sambad.moring.domain.meeting.comment.application.reply.MeetingQuestionCommentReplyRepository;
import org.depromeet.sambad.moring.domain.meeting.comment.domain.reply.MeetingQuestionCommentReply;
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

	@Override
	public Optional<MeetingQuestionCommentReply> findById(Long id) {
		return meetingQuestionCommentReplyJpaRepository.findById(id);
	}

	@Override
	public void delete(MeetingQuestionCommentReply commentReply) {
		meetingQuestionCommentReplyJpaRepository.delete(commentReply);
	}
}
