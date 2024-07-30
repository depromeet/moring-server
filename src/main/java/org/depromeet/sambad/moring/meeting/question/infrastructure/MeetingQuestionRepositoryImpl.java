package org.depromeet.sambad.moring.meeting.question.infrastructure;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.meeting.question.application.MeetingQuestionRepository;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.response.ActiveMeetingQuestionResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.FullInactiveMeetingQuestionListResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MeetingQuestionStatisticsDetail;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MostInactiveMeetingQuestionListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MeetingQuestionRepositoryImpl implements MeetingQuestionRepository {

	private final MeetingQuestionJpaRepository meetingQuestionJpaRepository;
	private final MeetingQuestionQueryRepository meetingQuestionQueryRepository;

	@Override
	public MeetingQuestion save(MeetingQuestion meetingQuestion) {
		return meetingQuestionJpaRepository.save(meetingQuestion);
	}

	@Override
	public Optional<MeetingQuestion> findNextQuestion(Long meetingId) {
		return meetingQuestionQueryRepository.findNextQuestion(meetingId);
	}

	@Override
	public boolean existsByQuestion(Long meetingId, Long questionId) {
		return meetingQuestionJpaRepository.existsByMeetingIdAndQuestionId(meetingId, questionId);
	}

	@Override
	public ActiveMeetingQuestionResponse findActiveOneByMeeting(Long meetingId, Long loginMeetingMemberId) {
		return meetingQuestionQueryRepository.findActiveOneByMeeting(meetingId, loginMeetingMemberId);
	}

	@Override
	public Optional<MeetingQuestion> findActiveOneByMeeting(Long meetingId) {
		return meetingQuestionQueryRepository.findActiveOneByMeeting(meetingId);
	}

	@Override
	public MostInactiveMeetingQuestionListResponse findMostInactiveList(Long meetingId) {
		return meetingQuestionQueryRepository.findMostInactiveList(meetingId);
	}

	@Override
	public FullInactiveMeetingQuestionListResponse findFullInactiveList(Long meetingId, Pageable pageable) {
		return meetingQuestionQueryRepository.findFullInactiveList(meetingId, pageable);
	}

	@Override
	public Optional<MeetingQuestion> findByMeetingIdAndMeetingQuestionId(Long meetingId, Long meetingQuestionId) {
		return meetingQuestionJpaRepository.findByMeetingIdAndId(meetingId, meetingQuestionId);
	}

	@Override
	public List<MeetingQuestionStatisticsDetail> findStatistics(Long meetingQuestionId) {
		return meetingQuestionQueryRepository.findStatistics(meetingQuestionId);
	}
}
