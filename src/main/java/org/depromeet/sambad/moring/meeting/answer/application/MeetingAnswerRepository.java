package org.depromeet.sambad.moring.meeting.answer.application;

import java.util.List;

import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.meeting.answer.presentation.response.MeetingAnswerListResponse;
import org.depromeet.sambad.moring.meeting.answer.presentation.response.MyMeetingAnswerListResponse;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;

public interface MeetingAnswerRepository {

	void save(MeetingAnswer meetingAnswer);

	boolean existsByMeetingMember(Long meetingQuestionId, Long meetingMemberId);

	boolean isAllAnsweredByMeetingIdAndMeetingQuestionId(Long meetingId, Long meetingQuestionId);

	List<MeetingAnswer> findMostSelected(Long meetingQuestionId);

	List<MeetingAnswer> findByMeetingQuestionIdAndMeetingMemberId(Long meetingQuestionId, Long meetingMemberId);

	List<MeetingMember> findMeetingMembersSelectWith(Long meetingQuestionId, List<Long> answerIds);

	MeetingAnswerListResponse findAllByMeetingMemberId(Long meetingMemberId);

	MyMeetingAnswerListResponse findAllByMyMeetingMemberId(Long loginMemberId);

	List<MeetingAnswer> findAllByMeetingMemberIdAndMeetingQuestionIdNotIn(Long meetingMemberId,
		List<Long> activeMeetingQuestionIds);

	List<MeetingAnswer> findAllByMeetingMemberIdAndMeetingQuestionIdIn(Long meetingMemberId,
		List<Long> activeMeetingQuestionIds);
}