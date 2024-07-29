package org.depromeet.sambad.moring.meeting.question.infrastructure;

import static com.querydsl.jpa.JPAExpressions.*;
import static org.depromeet.sambad.moring.meeting.answer.domain.QMeetingAnswer.*;
import static org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion.*;
import static org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestionStatus.*;
import static org.depromeet.sambad.moring.meeting.question.domain.QMeetingQuestion.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.answer.domain.Answer;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.response.ActiveMeetingQuestionResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.FullInactiveMeetingQuestionListResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MostInactiveMeetingQuestionListResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MostInactiveMeetingQuestionListResponseDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MeetingQuestionQueryRepository {

	private final JPAQueryFactory queryFactory;

	public Optional<MeetingQuestion> findNextQuestion(Long meetingId) {
		MeetingQuestion nextQuestion = queryFactory
			.select(meetingQuestion)
			.from(meetingQuestion)
			.where(
				meetingQuestion.meeting.id.eq(meetingId),
				meetingQuestion.meetingQuestionStatus.eq(NOT_STARTED)
			)
			.orderBy(meetingQuestion.startTime.asc())
			.limit(1)
			.fetchFirst();
		return Optional.of(nextQuestion);
	}

	public ActiveMeetingQuestionResponse findActiveOneByMeeting(Long meetingId, Long loginMeetingMemberId) {
		Optional<MeetingQuestion> activeMeetingQuestion = findActiveQuestion(meetingId);

		if (activeMeetingQuestion.isEmpty()) {
			return null;
		}
		if (activeMeetingQuestion.get().getQuestion() == null) {
			return ActiveMeetingQuestionResponse.questionNotRegisteredOf(activeMeetingQuestion.get());
		}
		return ActiveMeetingQuestionResponse.questionRegisteredOf(activeMeetingQuestion.get(),
			isAnswered(activeMeetingQuestion.get().getId(), loginMeetingMemberId));
	}

	public Optional<MeetingQuestion> findActiveOneByMeeting(Long meetingId) {
		return findActiveQuestion(meetingId);
	}

	public MostInactiveMeetingQuestionListResponse findMostInactiveList(Long meetingId, Long loginMeetingMemberId) {
		List<MeetingQuestion> inactiveMeetingQuestions = queryFactory
			.select(meetingQuestion)
			.from(meetingQuestion)
			.where(
				meetingQuestion.meeting.id.eq(meetingId),
				inactiveCond()
			)
			.orderBy(orderDescByMeetingAnswerCount())
			.limit(2)
			.fetch();

		List<MostInactiveMeetingQuestionListResponseDetail> responseDetails = inactiveMeetingQuestions.stream()
			.map(meetingQuestion -> MostInactiveMeetingQuestionListResponseDetail.of(meetingQuestion,
				getBestAnswer(meetingQuestion)))
			.toList();
		return MostInactiveMeetingQuestionListResponse.from(responseDetails);
	}

	public FullInactiveMeetingQuestionListResponse findFullInactiveList(Long meetingId, Long loginMeetingMemberId,
		Pageable pageable) {
		List<MeetingQuestion> inactiveMeetingQuestions = queryFactory
			.select(meetingQuestion)
			.from(meetingQuestion)
			.where(
				meetingQuestion.meeting.id.eq(meetingId),
				inactiveCond()
			)
			.orderBy(orderDescByMeetingAnswerCount())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return FullInactiveMeetingQuestionListResponse.of(inactiveMeetingQuestions, pageable);
	}

	private Optional<MeetingQuestion> findActiveQuestion(Long meetingId) {
		return Optional.ofNullable(
			queryFactory
				.selectFrom(meetingQuestion)
				.where(meetingQuestion.meeting.id.eq(meetingId),
					activeCond())
				.fetchOne()
		);
	}

	private OrderSpecifier<Long> orderDescByMeetingAnswerCount() {
		// 서브쿼리를 사용하여 각 meetingQuestion에 대한 답변 수를 계산
		NumberExpression<Long> count = meetingAnswer.count();

		// OrderSpecifier를 사용하여 내림차순 정렬
		return new OrderSpecifier<>(Order.DESC,
			select(count)
				.from(meetingAnswer)
				.groupBy(meetingAnswer.meetingQuestion));
	}

	private Answer getBestAnswer(MeetingQuestion meetingQuestion) {
		return queryFactory
			.select(meetingAnswer.answer)
			.from(meetingAnswer)
			.where(meetingAnswer.meetingQuestion.eq(meetingQuestion))
			.groupBy(meetingAnswer.answer)
			.orderBy(meetingAnswer.count().desc())
			.fetchOne();
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

	private BooleanExpression activeCond() {
		LocalDateTime now = LocalDateTime.now();
		return meetingQuestion.startTime.loe(now)
			.and(meetingQuestion.startTime.goe(now.minusHours(RESPONSE_TIME_LIMIT_HOURS)))
			.and(isAnsweredByAllCond().not());
	}

	private BooleanExpression inactiveCond() {
		LocalDateTime now = LocalDateTime.now();
		return meetingQuestion.startTime.gt(now)
			.or(meetingQuestion.startTime.lt(now.minusHours(RESPONSE_TIME_LIMIT_HOURS)))
			.or(isAnsweredByAllCond());
	}

	private BooleanExpression isAnsweredByAllCond() {
		return meetingQuestion.memberAnswers.size().eq(meetingQuestion.meeting.meetingMembers.size());
	}
}
