package org.depromeet.sambad.moring.meeting.comment.infrastructure;

import java.util.List;

import org.depromeet.sambad.moring.meeting.comment.domain.MeetingQuestionComment;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingQuestionCommentJpaRepository extends JpaRepository<MeetingQuestionComment, Long> {
	List<MeetingQuestionComment> findAllByMeetingQuestion(MeetingQuestion meetingQuestion);
}
