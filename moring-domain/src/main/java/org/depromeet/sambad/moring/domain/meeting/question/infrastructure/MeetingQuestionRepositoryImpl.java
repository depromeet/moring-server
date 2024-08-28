package org.depromeet.sambad.moring.domain.meeting.question.infrastructure;

import static org.depromeet.sambad.moring.domain.meeting.question.domain.MeetingQuestionStatus.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.domain.meeting.question.application.MeetingQuestionRepository;
import org.depromeet.sambad.moring.domain.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.domain.meeting.question.domain.MeetingQuestionStatus;
import org.depromeet.sambad.moring.domain.meeting.question.presentation.response.FullInactiveMeetingQuestionListResponse;
import org.depromeet.sambad.moring.domain.meeting.question.presentation.response.MeetingQuestionStatisticsDetail;
import org.depromeet.sambad.moring.domain.meeting.question.presentation.response.MostInactiveMeetingQuestionListResponse;
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
	public Optional<MeetingQuestion> findCurrentOne(Long meetingId) {
		return meetingQuestionJpaRepository.findFirstByMeetingIdAndStatusOrderByStartTime(meetingId, ACTIVE);
	}

	@Override
	public Optional<MeetingQuestion> findCurrentActiveOne(Long meetingId) {
		return meetingQuestionJpaRepository.findFirstByMeetingIdAndStatusAndQuestionIsNotNullOrderByStartTime(meetingId,
			ACTIVE);
	}

	@Override
	public MostInactiveMeetingQuestionListResponse findTopInactiveList(Long meetingId) {
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
	public Optional<MeetingQuestion> findFirstByMeetingIdAndStatusAndStartTimeAfterOrderByStartTime(
		Long meetingId, MeetingQuestionStatus status, LocalDateTime now) {
		return meetingQuestionJpaRepository.findFirstByMeetingIdAndStatusAndStartTimeAfterOrderByStartTime(
			meetingId, status, now);
	}

	@Override
	public List<MeetingQuestionStatisticsDetail> findStatistics(Long meetingQuestionId) {
		return meetingQuestionQueryRepository.findStatistics(meetingQuestionId);
	}

	@Override
	public List<MeetingMember> findMeetingMembersByMeetingQuestionId(Long meetingQuestionId) {
		return meetingQuestionQueryRepository.findMeetingMembersByMeetingQuestionId(meetingQuestionId);
	}

	@Override
	public List<MeetingQuestion> findAllByStatusAndExpiredAtBefore(MeetingQuestionStatus status, LocalDateTime now) {
		return meetingQuestionJpaRepository.findAllByStatusAndExpiredAtBefore(status, now);
	}

	@Override
	public List<MeetingQuestion> findAllInactiveAndQuestionNotRegistered() {
		return meetingQuestionQueryRepository.findAllInactiveAndQuestionNotRegistered();
	}

	@Override
	public Optional<MeetingQuestion> findFirstByMeetingIdAndStatus(Long meetingId, MeetingQuestionStatus status) {
		return meetingQuestionJpaRepository.findFirstByMeetingIdAndStatusOrderByStartTime(meetingId, status);
	}

	@Override
	public boolean isAnswered(Long meetingQuestionId, Long meetingMemberId) {
		return meetingQuestionQueryRepository.isAnswered(meetingQuestionId, meetingMemberId);
	}
}
