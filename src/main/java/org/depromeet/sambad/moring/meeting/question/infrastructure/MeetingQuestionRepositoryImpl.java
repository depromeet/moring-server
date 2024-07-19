package org.depromeet.sambad.moring.meeting.question.infrastructure;

import static org.depromeet.sambad.moring.meeting.answer.domain.QMeetingMemberAnswer.*;
import static org.depromeet.sambad.moring.meeting.member.domain.QMeetingMember.*;
import static org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion.*;
import static org.depromeet.sambad.moring.meeting.question.domain.QMeetingQuestion.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.common.response.PageableResponse;
import org.depromeet.sambad.moring.meeting.question.application.MeetingQuestionRepository;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MeetingQuestionListResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MeetingQuestionResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Ops;
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
	public MeetingQuestionResponse findActiveOneByMeeting(
		Long meetingId, Long loginMeetingMemberId) {
		Optional<MeetingQuestion> activeMeetingQuestion = Optional.ofNullable(
			queryFactory
				.selectFrom(meetingQuestion)
				.where(meetingQuestion.meeting.id.eq(meetingId),
					activeCond())
				.fetchOne()
		);

		if (activeMeetingQuestion.isEmpty()) {
			return null;
		}
		return MeetingQuestionResponse.of(activeMeetingQuestion.get(),
			isAnswered(activeMeetingQuestion.get().getId(), loginMeetingMemberId));
	}

	@Override
	public MeetingQuestionListResponse findInactiveList(Long meetingId, Long loginMeetingMemberId, Pageable pageable) {
		List<MeetingQuestion> inactiveMeetingQuestions = queryFactory
			.select(meetingQuestion)
			.from(meetingQuestion)
			.where(
				meetingQuestion.meeting.id.eq(meetingId),
				inactiveCond()
			)
			.orderBy(meetingQuestion.startTime.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		List<MeetingQuestionResponse> meetingQuestionResponses = inactiveMeetingQuestions.stream()
			.map(meetingQuestion -> MeetingQuestionResponse.of(meetingQuestion,
				isAnswered(meetingQuestion.getId(), loginMeetingMemberId)))
			.toList();

		int totalMeetingMemberCount = queryFactory
			.select(meetingMember.count())
			.from(meetingMember)
			.where(meetingMember.meeting.id.eq(meetingId))
			.fetchOne()
			.intValue();

		return MeetingQuestionListResponse.of(totalMeetingMemberCount,
			meetingQuestionResponses,
			PageableResponse.of(pageable, meetingQuestionResponses));
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

	private BooleanExpression activeCond() {
		DateTimeExpression<LocalDateTime> endTime = getEndTime();
		DateTimeExpression<LocalDateTime> now = getNow();

		return now.loe(endTime);
	}

	private BooleanExpression inactiveCond() {
		DateTimeExpression<LocalDateTime> endTime = getEndTime();
		DateTimeExpression<LocalDateTime> now = getNow();

		return now.gt(endTime);
	}

	private DateTimeExpression<LocalDateTime> getEndTime() {
		DateTimeExpression<LocalDateTime> endTime = Expressions.dateTimeTemplate(
			LocalDateTime.class,
			"{0} + {1} hours",
			meetingQuestion.startTime,
			RESPONSE_TIME_LIMIT_HOURS
		);
		return endTime;
	}

	private DateTimeExpression<LocalDateTime> getNow() {
		return Expressions.dateTimeOperation(LocalDateTime.class, Ops.DateTimeOps.CURRENT_TIMESTAMP);
	}
}
