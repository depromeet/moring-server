package org.depromeet.sambad.moring.meeting.answer.infrastructure;

import java.util.List;

import org.depromeet.sambad.moring.meeting.answer.application.MeetingAnswerRepository;
import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.meeting.answer.presentation.response.MyMeetingAnswerListResponse;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MeetingAnswerRepositoryImpl implements MeetingAnswerRepository {

	private final MeetingAnswerJpaRepository meetingAnswerJpaRepository;
	private final MeetingAnswerQueryRepository meetingAnswerQueryRepository;

	@Override
	public void save(MeetingAnswer meetingAnswer) {
		meetingAnswerJpaRepository.save(meetingAnswer);
	}

	@Override
	public boolean existsByMeetingMember(Long meetingQuestionId, Long meetingMemberId) {
		return meetingAnswerJpaRepository.existsByMeetingQuestionIdAndMeetingMemberId(meetingQuestionId,
			meetingMemberId);
	}

	@Override
	public boolean isAllAnsweredByMeetingIdAndMeetingQuestionId(Long meetingId, Long meetingQuestionId) {
		return meetingAnswerQueryRepository.isAllAnsweredByMeetingIdAndMeetingQuestionId(meetingId, meetingQuestionId);
	}

	@Override
	public List<MeetingAnswer> findMostSelected(Long meetingQuestionId) {
		return meetingAnswerQueryRepository.findMostSelectedMeetingAnswer(meetingQuestionId);
	}

	@Override
	public List<MeetingAnswer> findByMeetingQuestionIdAndMeetingMemberId(Long meetingQuestionId, Long meetingMemberId) {
		return meetingAnswerJpaRepository.findByMeetingQuestionIdAndMeetingMemberId(meetingQuestionId, meetingMemberId);
	}

	@Override
	public List<MeetingMember> findMeetingMembersSelectWith(Long questionId, List<Long> answerIds) {
		return meetingAnswerQueryRepository.findSameAnswerSelectMembers(questionId, answerIds);
	}

	@Override
	public MyMeetingAnswerListResponse findAllByMeetingMemberId(Long meetingMemberId) {
		return meetingAnswerQueryRepository.findAllByMeetingMemberId(meetingMemberId);
	}
}