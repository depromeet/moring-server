package org.depromeet.sambad.moring.domain.meeting.comment.infrastructure.comment;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.domain.meeting.comment.application.comment.MeetingQuestionCommentRepository;
import org.depromeet.sambad.moring.domain.meeting.comment.domain.comment.MeetingQuestionComment;
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
	public Optional<MeetingQuestionComment> findByIdAndMeetingQuestionId(Long id, Long meetingQuestionId) {
		return meetingQuestionCommentJpaRepository.findByIdAndMeetingQuestionId(id, meetingQuestionId);
	}

	@Override
	public void delete(MeetingQuestionComment meetingQuestionComment) {
		meetingQuestionCommentJpaRepository.delete(meetingQuestionComment);
	}

	@Override
	public List<MeetingQuestionComment> findAllByMeetingQuestionId(Long meetingQuestionId) {
		return meetingQuestionCommentJpaRepository.findAllByMeetingQuestionId(meetingQuestionId);
	}
}
