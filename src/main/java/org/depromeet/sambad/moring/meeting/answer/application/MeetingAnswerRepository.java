package org.depromeet.sambad.moring.meeting.answer.application;

import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.meeting.answer.presentation.response.MyMeetingAnswerListResponse;

public interface MeetingAnswerRepository {

	void save(MeetingAnswer meetingAnswer);

	boolean existsByMeetingMember(Long meetingQuestionId, Long meetingMemberId);

	MyMeetingAnswerListResponse findAllByMeetingMemberId(Long meetingMemberId);
}