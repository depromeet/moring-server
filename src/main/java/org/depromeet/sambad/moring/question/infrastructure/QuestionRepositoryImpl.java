package org.depromeet.sambad.moring.question.infrastructure;

import static com.querydsl.core.types.dsl.Expressions.*;
import static com.querydsl.jpa.JPAExpressions.*;
import static org.depromeet.sambad.moring.meeting.question.domain.QMeetingQuestion.*;
import static org.depromeet.sambad.moring.question.domain.QQuestion.*;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.common.response.PageableResponse;
import org.depromeet.sambad.moring.question.application.QuestionRepository;
import org.depromeet.sambad.moring.question.domain.Question;
import org.depromeet.sambad.moring.question.presentation.response.QuestionListResponse;
import org.depromeet.sambad.moring.question.presentation.response.QuestionSummaryResponse;
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
	public QuestionListResponse findQuestionsByMeeting(Long meetingId, Pageable pageable) {
		List<Long> usedQuestionIds = queryFactory
			.select(meetingQuestion.question.id)
			.from(meetingQuestion)
			.where(meetingIdEq(meetingId))
			.orderBy(meetingQuestion.question.createAt.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		List<QuestionSummaryResponse> questionSummaryResponses = queryFactory
			.select(Projections.constructor(
				QuestionSummaryResponse.class,
				question.id.as("questionId"),
				question.questionImage.physicalPath.as("questionImageFileUrl"),
				question.title,
				as(select(meetingQuestion.count())
					.from(meetingQuestion)
					.where(questionEq()), "usedCount")
			))
			.from(question)
			.where(question.id.notIn(usedQuestionIds))
			.orderBy(question.createAt.asc())
			.fetch();

		List<Long> total = queryFactory
			.select(question.id)
			.from(question)
			.where(question.id.notIn(usedQuestionIds))
			.fetch();
		int totalPages = total.size() / pageable.getPageSize();
		boolean isEnd = totalPages == (pageable.getPageNumber() + 1);

		PageableResponse pageableResponse = PageableResponse.builder()
			.page(pageable.getPageNumber())
			.size(pageable.getPageSize())
			.totalPages(totalPages)
			.isEnd(isEnd)
			.build();

		return QuestionListResponse.of(questionSummaryResponses, pageableResponse);
	}

	private static BooleanExpression questionEq() {
		return meetingQuestion.question.id.eq(question.id);
	}

	private BooleanExpression meetingIdEq(Long meetingId) {
		return meetingQuestion.meeting.id.eq(meetingId);
	}
}
