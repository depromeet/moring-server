package org.depromeet.sambad.moring.meeting.comment.application;

import java.util.Optional;

import org.depromeet.sambad.moring.meeting.comment.domain.MeetingQuestionComment;

public interface MeetingQuestionCommentRepository {
	void save(MeetingQuestionComment meetingQuestionComment);

	Optional<MeetingQuestionComment> findById(Long id);

	void delete(MeetingQuestionComment meetingQuestionComment);
}
