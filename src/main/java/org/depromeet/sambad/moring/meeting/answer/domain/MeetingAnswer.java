package org.depromeet.sambad.moring.meeting.answer.domain;

import org.depromeet.sambad.moring.answer.domain.Answer;
import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingAnswer extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meeting_question_id")
	private MeetingQuestion meetingQuestion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "answer_id")
	private Answer answer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meeting_member_id")
	private MeetingMember meetingMember;

	@Builder
	public MeetingAnswer(MeetingQuestion meetingQuestion, Answer answer, MeetingMember meetingMember) {
		this.meetingQuestion = meetingQuestion;
		this.answer = answer;
		this.meetingMember = meetingMember;
	}
}