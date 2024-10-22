package org.depromeet.sambad.moring.domain.meeting.answer.infrastructure;

import java.util.List;

import org.depromeet.sambad.moring.domain.meeting.answer.application.MeetingAnswerRepository;
import org.depromeet.sambad.moring.domain.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.domain.meeting.answer.presentation.response.MeetingAnswerListResponse;
import org.depromeet.sambad.moring.domain.meeting.answer.presentation.response.MyMeetingAnswerListResponse;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;
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
	public void updateAllHiddenByMeetingMemberId(Long meetingMemberId) {
		meetingAnswerJpaRepository.updateAllHiddenByMeetingMemberId(meetingMemberId);
	}

	@Override
	public void updateManyHiddenByMeetingMemberIdAndMeetingQuestionId(Long meetingMemberId,
		List<Long> meetingQuestionIds) {
		meetingAnswerJpaRepository.updateManyHiddenByMeetingMemberIdAndMeetingQuestionId(meetingMemberId,
			meetingQuestionIds);
	}

	@Override
	public void updateManyActivateByMeetingMemberIdAndMeetingQuestionId(Long meetingMemberId,
		List<Long> meetingQuestionIds) {
		meetingAnswerJpaRepository.updateManyActivateByMeetingMemberIdAndMeetingQuestionId(meetingMemberId,
			meetingQuestionIds);
	}
}