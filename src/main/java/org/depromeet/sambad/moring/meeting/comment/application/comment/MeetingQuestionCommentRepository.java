package org.depromeet.sambad.moring.meeting.comment.application.comment;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.meeting.comment.domain.comment.MeetingQuestionComment;

public interface MeetingQuestionCommentRepository {
	void save(MeetingQuestionComment meetingQuestionComment);

	Optional<MeetingQuestionComment> findByIdAndMeetingQuestionId(Long id, Long meetingQuestionId);

	void delete(MeetingQuestionComment meetingQuestionComment);

	List<MeetingQuestionComment> findAllByMeetingQuestionId(Long meetingQuestionId);
}
