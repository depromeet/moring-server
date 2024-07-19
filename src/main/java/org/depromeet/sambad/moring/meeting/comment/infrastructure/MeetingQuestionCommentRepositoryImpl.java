package org.depromeet.sambad.moring.meeting.comment.infrastructure;

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
}
