package org.depromeet.sambad.moring.meeting.answer.infrastructure;

import static com.querydsl.core.types.dsl.Expressions.*;
import static com.querydsl.jpa.JPAExpressions.*;
import static org.depromeet.sambad.moring.meeting.answer.domain.QMeetingAnswer.*;
import static org.depromeet.sambad.moring.meeting.comment.domain.comment.QMeetingQuestionComment.*;

import java.util.List;

import org.depromeet.sambad.moring.meeting.answer.application.MeetingAnswerRepository;
import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.meeting.answer.infrastructure.dto.MyMeetingAnswerResponseCustom;
import org.depromeet.sambad.moring.meeting.answer.presentation.response.MyMeetingAnswerListResponse;
import org.depromeet.sambad.moring.meeting.comment.domain.comment.MeetingQuestionComment;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MeetingAnswerRepositoryImpl implements MeetingAnswerRepository {

	private final MeetingAnswerJpaRepository meetingAnswerJpaRepository;
	private final JPAQueryFactory queryFactory;
	private final MeetingAnswerQueryRepository meetingAnswerQueryRepository;

	@Override
	public void save(MeetingAnswer meetingAnswer) {
		meetingAnswerJpaRepository.save(meetingAnswer);
	}

	@Override
	public List<MeetingAnswer> findMostSelected(Long meetingQuestionId) {
		return meetingAnswerQueryRepository.findMostSelectedMeetingAnswer(meetingQuestionId);
	}

	@Override
	public List<MeetingAnswer> findByMeetingQuestionIdAndMeetingMemberId(Long meetingQuestionId, Long meetingMemberId) {
		return meetingAnswerJpaRepository.findByMeetingQuestionIdAndMeetingMemberId(meetingQuestionId, meetingMemberId);
	}

	@Override
	public List<MeetingMember> findMeetingMembersSelectWith(Long questionId, List<Long> answerIds) {
		return meetingAnswerQueryRepository.findSameAnswerSelectMembers(questionId, answerIds);
	}

	@Override
	public boolean existsByMeetingMember(Long meetingQuestionId, Long meetingMemberId) {
		return meetingAnswerJpaRepository.existsByMeetingQuestionIdAndMeetingMemberId(meetingQuestionId,
			meetingMemberId);
	}

	@Override
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