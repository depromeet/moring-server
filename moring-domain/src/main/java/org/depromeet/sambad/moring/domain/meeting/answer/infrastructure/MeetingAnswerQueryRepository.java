package org.depromeet.sambad.moring.domain.meeting.answer.infrastructure;

import static com.querydsl.core.types.dsl.Expressions.*;
import static org.depromeet.sambad.moring.domain.meeting.answer.domain.QMeetingAnswer.*;
import static org.depromeet.sambad.moring.domain.meeting.comment.domain.comment.QMeetingQuestionComment.*;
import static org.depromeet.sambad.moring.domain.meeting.member.domain.QMeetingMember.*;
import static org.depromeet.sambad.moring.domain.meeting.question.domain.MeetingQuestionStatus.*;
import static org.depromeet.sambad.moring.domain.meeting.question.domain.QMeetingQuestion.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.depromeet.sambad.moring.domain.answer.domain.Answer;
import org.depromeet.sambad.moring.domain.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.domain.meeting.answer.infrastructure.dto.MeetingAnswerResponseCustom;
import org.depromeet.sambad.moring.domain.meeting.answer.infrastructure.dto.MyMeetingAnswerResponseCustom;
import org.depromeet.sambad.moring.domain.meeting.answer.presentation.response.MeetingAnswerListResponse;
import org.depromeet.sambad.moring.domain.meeting.answer.presentation.response.MyMeetingAnswerListResponse;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.domain.meeting.question.domain.MeetingQuestion;
import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MeetingAnswerQueryRepository {

	private final JPAQueryFactory queryFactory;

	public boolean isAllAnsweredByMeetingIdAndMeetingQuestionId(Long meetingId, Long meetingQuestionId) {
		Long answeredMeetingMemberCount = queryFactory.select(meetingAnswer.meetingMember.countDistinct())
			.from(meetingAnswer)
			.where(meetingAnswer.meetingQuestion.id.eq(meetingQuestionId))
			.groupBy(meetingAnswer.meetingQuestion.id)
			.fetchOne();

		Long allMemberCount = queryFactory.select(meetingMember.count())
			.from(meetingMember)
			.where(meetingMember.meeting.id.eq(meetingId))
			.fetchOne();

		return Objects.equals(answeredMeetingMemberCount, allMemberCount);
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
			.where(
				meetingAnswer.answer.id.eq(mostSelectedAnswerId),
				meetingAnswer.meetingQuestion.id.eq(meetingQuestionId)
			)
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

	public MeetingAnswerListResponse findAllByOtherMeetingMemberId(Long meetingMemberId) {

		List<MeetingQuestion> meetingQuestions = queryFactory.select(meetingQuestion)
			.from(meetingQuestion)
			.join(meetingAnswer).on(meetingQuestion.eq(meetingAnswer.meetingQuestion)).fetchJoin()
			.join(meetingMember).on(meetingMember.eq(meetingAnswer.meetingMember)).fetchJoin()
			.where(meetingMember.id.eq(meetingMemberId),
				inactiveCond(),
				meetingAnswer.isHidden.isFalse())
			.orderBy(meetingQuestion.createdAt.asc())
			.fetch();

		List<MeetingAnswerResponseCustom> responseCustoms = meetingQuestions.stream()
			.map(meetingQuestion -> new MeetingAnswerResponseCustom(meetingQuestion,
				getMyAnswers(meetingMemberId, meetingQuestion),
				getMyComment(meetingMemberId, meetingQuestion)))
			.toList();

		return MeetingAnswerListResponse.from(responseCustoms);
	}

	public MyMeetingAnswerListResponse findAllByMyMeetingMemberId(Long meetingMemberId) {

		List<MeetingQuestion> meetingQuestions = queryFactory.select(meetingQuestion)
			.from(meetingQuestion)
			.join(meetingAnswer).on(meetingQuestion.eq(meetingAnswer.meetingQuestion)).fetchJoin()
			.join(meetingMember).on(meetingMember.eq(meetingAnswer.meetingMember)).fetchJoin()
			.where(meetingMember.id.eq(meetingMemberId),
				inactiveCond())
			.orderBy(meetingQuestion.createdAt.asc())
			.fetch();

		List<MyMeetingAnswerResponseCustom> responseCustoms = meetingQuestions.stream()
			.map(meetingQuestion -> new MyMeetingAnswerResponseCustom(
				meetingQuestion,
				getMyAnswers(meetingMemberId, meetingQuestion),
				getMyComment(meetingMemberId, meetingQuestion),
				isHidden(meetingQuestion, meetingMemberId)
			))
			.toList();

		return MyMeetingAnswerListResponse.from(responseCustoms);
	}

	private List<Answer> getMyAnswers(Long memberId, MeetingQuestion meetingQuestion) {
		return queryFactory.select(meetingAnswer.answer)
			.from(meetingAnswer)
			.where(meetingAnswer.meetingQuestion.id.eq(meetingQuestion.getId()),
				meetingAnswer.meetingMember.id.eq(memberId))
			.fetch();
	}

	private String getMyComment(Long memberId, MeetingQuestion meetingQuestion) {
		return queryFactory.select(meetingQuestionComment.content)
			.from(meetingQuestionComment)
			.where(meetingQuestionComment.meetingMember.id.eq(memberId),
				meetingQuestionComment.meetingQuestion.eq(meetingQuestion))
			.fetchFirst();
	}

	private Boolean isHidden(MeetingQuestion meetingQuestion, Long loginMemberId) {
		MeetingAnswer answerOfList = queryFactory.selectFrom(meetingAnswer)
			.where(meetingAnswer.meetingQuestion.eq(meetingQuestion),
				meetingAnswer.meetingMember.id.eq(loginMemberId))
			.fetchFirst();
		return answerOfList.getIsHidden();
	}

	private BooleanExpression inactiveCond() {
		return meetingQuestion.expiredAt.lt(LocalDateTime.now())
			.or(meetingQuestion.status.eq(INACTIVE));
	}
}
