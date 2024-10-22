package org.depromeet.sambad.moring.domain.question.infrastructure;

import static com.querydsl.core.types.dsl.Expressions.*;
import static com.querydsl.jpa.JPAExpressions.*;
import static org.depromeet.sambad.moring.domain.meeting.question.domain.QMeetingQuestion.*;
import static org.depromeet.sambad.moring.domain.question.domain.QQuestion.*;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.domain.common.response.PageableResponse;
import org.depromeet.sambad.moring.domain.question.application.QuestionRepository;
import org.depromeet.sambad.moring.domain.question.domain.Question;
import org.depromeet.sambad.moring.domain.question.domain.QuestionSummaryDto;
import org.depromeet.sambad.moring.domain.question.presentation.response.QuestionListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {

	private final QuestionJpaRepository questionJpaRepository;
	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<Question> findById(Long id) {
		return questionJpaRepository.findById(id);
	}

	@Override
	public QuestionListResponse findQuestionsByMeetingId(Long meetingId, Pageable pageable) {
		List<Long> usedQuestionIds = queryFactory
			.select(meetingQuestion.question.id)
			.from(meetingQuestion)
			.where(
				meetingQuestion.meeting.id.eq(meetingId),
				meetingQuestion.question.id.isNotNull()
			)
			.fetch();

		List<QuestionSummaryDto> questionSummaryResponses = queryFactory
			.select(Projections.constructor(
				QuestionSummaryDto.class,
				question,
				as(select(meetingQuestion.count())
					.from(meetingQuestion)
					.where(questionEq()), "usedCount")
			))
			.from(question)
			.where(question.id.notIn(usedQuestionIds))
			.orderBy(question.createdAt.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		List<Long> totalElementIds = queryFactory
			.select(question.id)
			.from(question)
			.where(question.id.notIn(usedQuestionIds))
			.fetch();

		return QuestionListResponse.of(questionSummaryResponses, PageableResponse.of(pageable, totalElementIds));
	}

	@Override
	public List<Question> findAllByNotInQuestionIds(List<Long> excludeQuestionIds) {
		return queryFactory
			.selectFrom(question)
			.where(question.id.notIn(excludeQuestionIds))
			.fetch();
	}

	@Override
	public void save(Question question) {
		questionJpaRepository.save(question);
	}

	private static BooleanExpression questionEq() {
		return meetingQuestion.question.id.eq(question.id);
	}
}