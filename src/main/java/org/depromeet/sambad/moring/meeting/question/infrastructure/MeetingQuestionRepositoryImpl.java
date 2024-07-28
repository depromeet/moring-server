package org.depromeet.sambad.moring.meeting.question.infrastructure;

import static com.querydsl.jpa.JPAExpressions.*;
import static org.depromeet.sambad.moring.meeting.answer.domain.QMeetingAnswer.*;
import static org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion.*;
import static org.depromeet.sambad.moring.meeting.question.domain.QMeetingQuestion.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.answer.domain.Answer;
import org.depromeet.sambad.moring.meeting.question.application.MeetingQuestionRepository;
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
public class MeetingQuestionRepositoryImpl implements MeetingQuestionRepository {

	private final MeetingQuestionJpaRepository meetingQuestionJpaRepository;
	private final JPAQueryFactory queryFactory;

	@Override
	public MeetingQuestion save(MeetingQuestion meetingQuestion) {
		return meetingQuestionJpaRepository.save(meetingQuestion);
	}

	@Override
	public boolean existsByQuestion(Long meetingId, Long questionId) {
		return meetingQuestionJpaRepository.existsByMeetingIdAndQuestionId(meetingId, questionId);
	}

	@Override
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

	@Override
	public Optional<MeetingQuestion> findActiveOneByMeeting(Long meetingId) {
		return findActiveQuestion(meetingId);
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

	@Override
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

	@Override
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

	@Override
	public Optional<MeetingQuestion> findByMeetingIdAndMeetingQuestionId(Long meetingId, Long meetingQuestionId) {
		return meetingQuestionJpaRepository.findByMeetingIdAndId(meetingId, meetingQuestionId);
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
