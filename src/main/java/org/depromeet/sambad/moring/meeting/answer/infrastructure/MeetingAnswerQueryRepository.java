package org.depromeet.sambad.moring.meeting.answer.infrastructure;

import static com.querydsl.core.types.dsl.Expressions.*;
import static org.depromeet.sambad.moring.meeting.answer.domain.QMeetingAnswer.*;

import java.util.List;
import java.util.Objects;

import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MeetingAnswerQueryRepository {

	private final JPAQueryFactory query;

	// TODO: 가장 많이 선택된 답변이 여러개일 수 있으나, 현재 로직은 하나만 반환. 추후 기획에 따라 수정 필요.
	public List<MeetingAnswer> findMostSelectedMeetingAnswer(Long meetingQuestionId) {
		Long mostSelectedAnswerId = query.select(meetingAnswer.answer.id)
			.from(meetingAnswer)
			.where(meetingAnswer.meetingQuestion.id.eq(meetingQuestionId))
			.groupBy(meetingAnswer.answer.id)
			.orderBy(meetingAnswer.answer.id.count().desc())
			.fetchFirst();

		return query.selectFrom(meetingAnswer)
			.where(meetingAnswer.answer.id.eq(mostSelectedAnswerId))
			.fetch();
	}

	/**
	 * 주어진 답변 리스트와 동일한 답변을 선택한 회원을 조회합니다.<br />
	 * "," 기반으로 유저의 답변 리스트를 CONCAT하여 비교합니다.
	 */
	public List<MeetingMember> findSameAnswerSelectMembers(Long meetingQuestionId, List<Long> answerIds) {
		StringExpression answerIdsExpression = stringTemplate(
			"GROUP_CONCAT(DISTINCT {0})", meetingAnswer.answer.id)
			.as("answer_ids");

		List<Tuple> answerIdsByMember = query.select(meetingAnswer.meetingMember, answerIdsExpression)
			.from(meetingAnswer)
			.where(meetingAnswer.meetingQuestion.id.eq(meetingQuestionId)
				.and(meetingAnswer.answer.id.in(answerIds)))
			.groupBy(meetingAnswer.meetingMember.id)
			.fetch();

		String answerIdsToString = String.join(",", answerIds.stream().map(String::valueOf).toList());

		return answerIdsByMember.stream()
			.filter(tuple -> Objects.equals(tuple.get(answerIdsExpression), answerIdsToString))
			.map(tuple -> tuple.get(meetingAnswer.meetingMember))
			.toList();
	}
}
