package org.depromeet.sambad.moring.meeting.comment.application;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.meeting.comment.domain.MeetingQuestionComment;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

public interface MeetingQuestionCommentRepository {
	void save(MeetingQuestionComment meetingQuestionComment);

	Optional<MeetingQuestionComment> findById(Long id);

	void delete(MeetingQuestionComment meetingQuestionComment);

	List<MeetingQuestionComment> findAllByMeetingQuestion(MeetingQuestion meetingQuestion);
}
