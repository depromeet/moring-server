package org.depromeet.sambad.moring.meeting.question.infrastructure;

import static org.depromeet.sambad.moring.meeting.answer.domain.QMeetingMemberAnswer.*;
import static org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion.*;
import static org.depromeet.sambad.moring.meeting.question.domain.QMeetingQuestion.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.depromeet.sambad.moring.meeting.question.application.MeetingQuestionRepository;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.domain.QMeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.response.ActiveMeetingQuestionResponse;
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
	public ActiveMeetingQuestionResponse findActiveOneByMeeting(Long meetingId, Long loginMeetingMemberId) {
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
		return ActiveMeetingQuestionResponse.of(activeMeetingQuestion.get(),
			isAnswered(activeMeetingQuestion.get().getId(), loginMeetingMemberId));
	}

	private Boolean isAnswered(Long meetingQuestionId, Long meetingMemberId) {
		Integer fetchOne = queryFactory
			.selectOne()
			.from(meetingMemberAnswer)
			.where(meetingMemberAnswer.meetingQuestion.id.eq(meetingQuestionId),
				meetingMemberAnswer.meetingMember.id.eq(meetingMemberId))
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
