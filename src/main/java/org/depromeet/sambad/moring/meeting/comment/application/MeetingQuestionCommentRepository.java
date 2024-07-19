package org.depromeet.sambad.moring.meeting.comment.application;

import org.depromeet.sambad.moring.meeting.comment.domain.MeetingQuestionComment;

public interface MeetingQuestionCommentRepository {
	void save(MeetingQuestionComment meetingQuestionComment);
}
