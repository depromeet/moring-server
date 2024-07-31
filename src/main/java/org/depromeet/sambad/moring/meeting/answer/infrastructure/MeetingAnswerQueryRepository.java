package org.depromeet.sambad.moring.meeting.answer.infrastructure;

import static com.querydsl.core.types.dsl.Expressions.*;
import static com.querydsl.jpa.JPAExpressions.*;
import static org.depromeet.sambad.moring.meeting.answer.domain.QMeetingAnswer.*;
import static org.depromeet.sambad.moring.meeting.comment.domain.comment.QMeetingQuestionComment.*;
import static org.depromeet.sambad.moring.meeting.member.domain.QMeetingMember.*;

import java.util.List;
import java.util.Objects;

import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.meeting.answer.infrastructure.dto.MyMeetingAnswerResponseCustom;
import org.depromeet.sambad.moring.meeting.answer.presentation.response.MyMeetingAnswerListResponse;
import org.depromeet.sambad.moring.meeting.comment.domain.comment.MeetingQuestionComment;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MeetingAnswerQueryRepository {

	private final JPAQueryFactory queryFactory;

	public boolean isAllAnsweredByMeetingIdAndMeetingQuestionId(Long meetingId, Long meetingQuestionId) {
		return queryFactory.select(meetingAnswer.count())
			.from(meetingAnswer)
			.where(meetingAnswer.meetingQuestion.id.eq(meetingQuestionId))
			.fetchOne()
			.equals(queryFactory.select(meetingMember.count())
				.from(meetingMember)
				.where(meetingMember.meeting.id.eq(meetingId))
				.fetchOne());
	}

	// TODO: 가장 많이 선택된 답변이 여러개일 수 있으나, 현재 로직은 하나만 반환. 추후 기획에 따라 수정 필요.
	public List<MeetingAnswer> findMostSelectedMeetingAnswer(Long meetingQuestionId) {
		Long mostSelectedAnswerId = queryFactory.select(meetingAnswer.answer.id)
			.from(meetingAnswer)
			.where(meetingAnswer.meetingQuestion.id.eq(meetingQuestionId))
			.groupBy(meetingAnswer.answer.id)
			.orderBy(meetingAnswer.answer.id.count().desc())
			.fetchFirst();

		// 답변이 하나도 등록되지 않은 경우
		if (mostSelectedAnswerId == null) {
			return List.of();
		}

		return queryFactory.selectFrom(meetingAnswer)
			.where(meetingAnswer.answer.id.eq(mostSelectedAnswerId))
			.fetch();
	}

	/**
	 * 주어진 답변 리스트와 동일한 답변을 선택한 회원을 조회합니다.<br />
	 * "," 기반으로 유저의 답변 리스트를 CONCAT하여 비교합니다.
	 */
	public List<MeetingMember> findMeetingMembersSelectWith(Long meetingQuestionId, List<Long> answerIds) {
		StringExpression answerIdsExpression = stringTemplate(
			"GROUP_CONCAT(DISTINCT {0})", meetingAnswer.answer.id)
			.as("answer_ids");

		List<Tuple> answerIdsByMember = queryFactory.select(meetingMember, answerIdsExpression)
			.from(meetingMember)
			.join(meetingAnswer).on(meetingAnswer.meetingMember.eq(meetingMember))
			.where(meetingAnswer.meetingQuestion.id.eq(meetingQuestionId)
				.and(meetingAnswer.answer.id.in(answerIds)))
			.groupBy(meetingMember)
			.fetch();

		String answerIdsToString = String.join(",", answerIds.stream().map(String::valueOf).toList());

		return answerIdsByMember.stream()
			.filter(tuple -> Objects.equals(tuple.get(answerIdsExpression), answerIdsToString))
			.map(tuple -> tuple.get(meetingMember))
			.toList();
	}

	public MyMeetingAnswerListResponse findAllByMeetingMemberId(Long meetingMemberId) {
		List<MyMeetingAnswerResponseCustom> responseCustoms = queryFactory.select(Projections.constructor(
				MyMeetingAnswerResponseCustom.class,
				meetingAnswer.meetingQuestion.as("meetingQuestion"),
				meetingAnswer.as("meetingAnswer"),
				as(getMyComment(meetingMemberId), "comment")
			))
			.from(meetingAnswer)
			.where(meetingAnswer.meetingMember.id.eq(meetingMemberId))
			.fetch();

		return MyMeetingAnswerListResponse.from(responseCustoms);
	}

	private JPQLQuery<MeetingQuestionComment> getMyComment(Long memberId) {
		return select(meetingQuestionComment)
			.from(meetingQuestionComment)
			.where(meetingQuestionComment.meetingMember.id.eq(memberId),
				meetingQuestionComment.meetingQuestion.eq(meetingAnswer.meetingQuestion));
	}
}
