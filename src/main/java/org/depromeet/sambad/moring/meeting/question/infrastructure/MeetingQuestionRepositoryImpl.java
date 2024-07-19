package org.depromeet.sambad.moring.meeting.question.infrastructure;

import static org.depromeet.sambad.moring.meeting.answer.domain.QMeetingAnswer.*;
import static org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion.*;
import static org.depromeet.sambad.moring.meeting.question.domain.QMeetingQuestion.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.depromeet.sambad.moring.meeting.question.application.MeetingQuestionRepository;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.domain.QMeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MeetingQuestionResponse;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MeetingQuestionRepositoryImpl implements MeetingQuestionRepository {

	private final MeetingQuestionJpaRepository meetingQuestionJpaRepository;
	private final JPAQueryFactory queryFactory;

	@Override
	public void save(MeetingQuestion meetingQuestion) {
		meetingQuestionJpaRepository.save(meetingQuestion);
	}

	@Override
	public boolean existsByQuestion(Long meetingId, Long questionId) {
		return meetingQuestionJpaRepository.existsByMeetingIdAndQuestionId(meetingId, questionId);
	}

	@Override
	public MeetingQuestionResponse findActiveOneByMeeting(Long meetingId, Long loginMeetingMemberId) {
		Optional<MeetingQuestion> activeMeetingQuestion = Optional.ofNullable(
			queryFactory
				.selectFrom(meetingQuestion)
				.where(meetingQuestion.meeting.id.eq(meetingId),
					ongoingCond(meetingQuestion))
				.fetchOne()
		);

		if (activeMeetingQuestion.isEmpty()) {
			return null;
		}
		return MeetingQuestionResponse.of(activeMeetingQuestion.get(),
			isAnswered(activeMeetingQuestion.get().getId(), loginMeetingMemberId));
	}

	@Override
	public Optional<MeetingQuestion> findById(Long id) {
		return meetingQuestionJpaRepository.findById(id);
	}

	private Boolean isAnswered(Long meetingQuestionId, Long meetingMemberId) {
		Integer fetchOne = queryFactory
			.selectOne()
			.from(meetingAnswer)
			.where(meetingAnswer.meetingQuestion.id.eq(meetingQuestionId),
				meetingAnswer.meetingMember.id.eq(meetingMemberId))
			.fetchFirst();
		return fetchOne != null;
	}

	private BooleanExpression ongoingCond(QMeetingQuestion meetingQuestion) {
		DateTimeExpression<LocalDateTime> endTime = Expressions.dateTimeTemplate(
			LocalDateTime.class,
			"{0} + {1} hours",
			meetingQuestion.startTime,
			RESPONSE_TIME_LIMIT_HOURS
		);
		return meetingQuestion.startTime.loe(endTime);
	}
}
