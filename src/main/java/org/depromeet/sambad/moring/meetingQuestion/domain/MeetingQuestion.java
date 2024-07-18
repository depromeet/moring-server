package org.depromeet.sambad.moring.meetingQuestion.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.meetingAnswer.domain.MeetingMemberAnswer;
import org.depromeet.sambad.moring.question.domain.Question;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingQuestion extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// FIXME: Meeting에서 mappedBy = "meeting" 지정
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meeting_id")
	private Meeting meeting;

	// FIXME: MeetingMember에서 mappedBy="registeredMember" 지정
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meeting_member_id")
	private MeetingMember registeredMember;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id")
	private Question question;

	@OneToMany(mappedBy = "meetingQuestion", fetch = FetchType.LAZY)
	private List<MeetingMemberAnswer> memberAnswers = new ArrayList<>();

	private LocalDateTime startTime;

	@Builder
	public MeetingQuestion(Meeting meeting, MeetingMember registeredMember, Question question,
		List<MeetingMemberAnswer> memberAnswers, LocalDateTime now) {
		this.meeting = meeting;
		this.registeredMember = registeredMember;
		this.question = question;
		this.startTime = now;
	}
}
