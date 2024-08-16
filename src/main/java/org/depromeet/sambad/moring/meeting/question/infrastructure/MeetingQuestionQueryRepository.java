package org.depromeet.sambad.moring.meeting.question.infrastructure;

import static com.querydsl.jpa.JPAExpressions.*;
import static org.depromeet.sambad.moring.meeting.answer.domain.QMeetingAnswer.*;
import static org.depromeet.sambad.moring.meeting.member.domain.QMeetingMember.*;
import static org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestionStatus.*;
import static org.depromeet.sambad.moring.meeting.question.domain.QMeetingQuestion.*;
import static org.depromeet.sambad.moring.question.domain.QQuestion.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.answer.domain.Answer;
import org.depromeet.sambad.moring.file.domain.QFileEntity;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.domain.QMeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.response.FullInactiveMeetingQuestionListResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MeetingQuestionStatisticsDetail;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MostInactiveMeetingQuestionListResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MostInactiveMeetingQuestionListResponseDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
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
				meetingQuestion.status.eq(NOT_STARTED)
			)
			.orderBy(meetingQuestion.startTime.asc())
			.limit(1)
			.fetchFirst();
		return Optional.of(nextQuestion);
	}

	public MostInactiveMeetingQuestionListResponse findMostInactiveList(Long meetingId) {
		List<MeetingQuestion> inactiveMeetingQuestions = queryFactory
			.select(meetingQuestion)
			.from(meetingQuestion)
			.leftJoin(meetingQuestion.question, question).fetchJoin()
			.leftJoin(meetingQuestion.memberAnswers, meetingAnswer).fetchJoin()
			.where(
				meetingQuestion.meeting.id.eq(meetingId),
				meetingQuestion.question.isNotNull(),
				inactiveCond()
			)
			.orderBy(orderDescByMeetingAnswerCount(), meetingQuestion.startTime.desc())
			.limit(2)
			.fetch();

		List<MostInactiveMeetingQuestionListResponseDetail> responseDetails = inactiveMeetingQuestions.stream()
			.map(meetingQuestion -> MostInactiveMeetingQuestionListResponseDetail.of(meetingQuestion,
				getBestAnswer(meetingQuestion)))
			.toList();
		return MostInactiveMeetingQuestionListResponse.from(responseDetails);
	}

	public FullInactiveMeetingQuestionListResponse findFullInactiveList(Long meetingId, Pageable pageable) {
		QFileEntity profileImageFile = new QFileEntity("profileImageFile");
		QFileEntity questionImageFile = new QFileEntity("questionImageFile");

		List<MeetingQuestion> inactiveMeetingQuestions = queryFactory
			.select(meetingQuestion)
			.from(meetingQuestion)
			.leftJoin(meetingQuestion.targetMember, meetingMember).fetchJoin()
			.leftJoin(meetingQuestion.targetMember.profileImageFile, profileImageFile).fetchJoin()
			.leftJoin(meetingQuestion.question, question).fetchJoin()
			.leftJoin(meetingQuestion.question.questionImageFile, questionImageFile).fetchJoin()
			.where(
				meetingQuestion.meeting.id.eq(meetingId),
				meetingQuestion.question.isNotNull(),
				inactiveCond()
			)
			.orderBy(orderDescByMeetingAnswerCount(), meetingQuestion.startTime.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return FullInactiveMeetingQuestionListResponse.of(inactiveMeetingQuestions, pageable);
	}

	public Boolean isAnswered(Long meetingQuestionId, Long meetingMemberId) {
		Integer fetchOne = queryFactory
			.selectOne()
			.from(meetingAnswer)
			.where(meetingAnswer.meetingQuestion.id.eq(meetingQuestionId),
				meetingAnswer.meetingMember.id.eq(meetingMemberId))
			.fetchFirst();
		return fetchOne != null;
	}

	public List<MeetingQuestionStatisticsDetail> findStatistics(Long meetingQuestionId) {
		// Get total count for the percentage calculation
		Optional<Long> optionalTotalCount = Optional.ofNullable(queryFactory
			.select(meetingAnswer.count())
			.from(meetingAnswer)
			.where(meetingAnswer.meetingQuestion.id.eq(meetingQuestionId))
			.fetchOne());

		if (optionalTotalCount.isEmpty()) {
			return List.of();
		}

		long totalCount = optionalTotalCount.get();

		List<MeetingQuestionStatisticsDetail> details = queryFactory
			.select(Projections.constructor(MeetingQuestionStatisticsDetail.class,
				Expressions.constant(0), // rank will be handled later
				meetingAnswer.answer.content,
				meetingAnswer.answer.id.count().intValue().as("count"),
				meetingAnswer.answer.id.count().doubleValue().divide(totalCount)
					.multiply(100).castToNum(Integer.class).as("percentage")
			))
			.from(meetingAnswer)
			.where(meetingAnswer.meetingQuestion.id.eq(meetingQuestionId))
			.groupBy(meetingAnswer.answer.id, meetingAnswer.answer.content)
			.orderBy(meetingAnswer.answer.id.count().desc())
			.fetch();

		// Adjust rank based on the order
		for (int i = 0; i < details.size(); i++) {
			MeetingQuestionStatisticsDetail detail = details.get(i);
			details.set(i,
				new MeetingQuestionStatisticsDetail(i + 1, detail.answerContent(), detail.count(),
					detail.percentage()));
		}

		return details;
	}

	public List<MeetingMember> findMeetingMembersByMeetingQuestionId(Long meetingQuestionId) {
		return queryFactory.select(meetingMember)
			.from(meetingQuestion)
			.join(meetingQuestion.memberAnswers, meetingAnswer)
			.join(meetingAnswer.meetingMember, meetingMember)
			.where(meetingQuestion.id.eq(meetingQuestionId))
			.fetch();
	}

	/*
	 * 각 모임 별로, 가장 최근 만료된 질문이 비활성화 상태인 경우만 조회
	 *
	 * SELECT *
	 * FROM meeting_question mq
	 * WHERE mq.question_id IS NULL
	 * 		AND mq.status = 'INACTIVE'
	 * 		AND mq.expired_at= (
	 * 			SELECT MAX(mq2.expired_at)
	 * 			FROM meeting_question mq2
	 * 			WHERE mq2.meeting_id = mq.meeting_id
	 * 		);
	 */
	public List<MeetingQuestion> findAllInactiveAndQuestionNotRegistered() {
		QMeetingQuestion mq1 = new QMeetingQuestion("mq1");
		QMeetingQuestion mq2 = new QMeetingQuestion("mq2");

		JPQLQuery<LocalDateTime> maxExpiredAtSubQuery = select(mq2.expiredAt.max())
			.from(mq2)
			.where(mq2.meeting.id.eq(mq1.meeting.id));

		return queryFactory.selectFrom(mq1)
			.where(
				mq1.question.isNull(),
				mq1.status.eq(INACTIVE),
				mq1.expiredAt.eq(maxExpiredAtSubQuery)
			)
			.fetch();
	}

	private OrderSpecifier<Integer> orderDescByMeetingAnswerCount() {
		return new OrderSpecifier<>(Order.DESC, meetingQuestion.memberAnswers.size());
	}

	private Optional<Answer> getBestAnswer(MeetingQuestion meetingQuestion) {
		return Optional.ofNullable(queryFactory
			.select(meetingAnswer.answer)
			.from(meetingAnswer)
			.where(meetingAnswer.meetingQuestion.eq(meetingQuestion))
			.groupBy(meetingAnswer.answer)
			.orderBy(meetingAnswer.count().desc())
			.limit(1)
			.fetchOne());
	}

	private BooleanExpression inactiveCond() {
		return meetingQuestion.expiredAt.lt(LocalDateTime.now())
			.or(meetingQuestion.status.eq(INACTIVE));
	}
}
