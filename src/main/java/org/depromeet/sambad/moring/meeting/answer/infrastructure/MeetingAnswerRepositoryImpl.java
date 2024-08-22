package org.depromeet.sambad.moring.meeting.answer.infrastructure;

import java.util.List;

import org.depromeet.sambad.moring.meeting.answer.application.MeetingAnswerRepository;
import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.meeting.answer.presentation.response.MeetingAnswerListResponse;
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
	public List<MeetingMember> findMeetingMembersSelectWith(Long meetingQuestionId, List<Long> answerIds) {
		return meetingAnswerQueryRepository.findMeetingMembersSelectWith(meetingQuestionId, answerIds);
	}

	@Override
	public MeetingAnswerListResponse findAllByMeetingMemberId(Long meetingMemberId) {
		return meetingAnswerQueryRepository.findAllByOtherMeetingMemberId(meetingMemberId);
	}

	@Override
	public MyMeetingAnswerListResponse findAllByMyMeetingMemberId(Long loginMemberId) {
		return meetingAnswerQueryRepository.findAllByMyMeetingMemberId(loginMemberId);
	}

	@Override
	public List<MeetingAnswer> findAllByMeetingMemberIdAndMeetingQuestionIdIn(Long meetingMemberId,
		List<Long> activeMeetingQuestionIds) {
		return meetingAnswerJpaRepository.findAllByMeetingMemberIdAndMeetingQuestionIdIn(meetingMemberId,
			activeMeetingQuestionIds);
	}

	@Override
	public List<MeetingAnswer> findAllByMeetingMemberIdAndMeetingQuestionIdNotIn(Long meetingMemberId,
		List<Long> activeMeetingQuestionIds) {
		return meetingAnswerJpaRepository.findAllByMeetingMemberIdAndMeetingQuestionIdNotIn(meetingMemberId,
			activeMeetingQuestionIds);
	}
}