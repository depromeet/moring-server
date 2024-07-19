package org.depromeet.sambad.moring.meeting.answer.application;

import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;

public interface MeetingAnswerRepository {

	void save(MeetingAnswer meetingAnswer);

	boolean existsByMeetingMember(Long meetingQuestionId, Long meetingMemberId);
}