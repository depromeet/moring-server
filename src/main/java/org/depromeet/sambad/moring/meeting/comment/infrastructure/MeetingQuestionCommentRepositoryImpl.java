package org.depromeet.sambad.moring.meeting.comment.infrastructure;

import java.util.Optional;

import org.depromeet.sambad.moring.meeting.comment.application.MeetingQuestionCommentRepository;
import org.depromeet.sambad.moring.meeting.comment.domain.MeetingQuestionComment;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MeetingQuestionCommentRepositoryImpl implements MeetingQuestionCommentRepository {
	private final MeetingQuestionCommentJpaRepository meetingQuestionCommentJpaRepository;
	@Override
	public void save(MeetingQuestionComment meetingQuestionComment) {
		meetingQuestionCommentJpaRepository.save(meetingQuestionComment);
	}

	@Override
	public Optional<MeetingQuestionComment> findById(Long id) {
		return meetingQuestionCommentJpaRepository.findById(id);
	}

	@Override
	public void delete(MeetingQuestionComment meetingQuestionComment) {
		meetingQuestionCommentJpaRepository.delete(meetingQuestionComment);
	}
}
