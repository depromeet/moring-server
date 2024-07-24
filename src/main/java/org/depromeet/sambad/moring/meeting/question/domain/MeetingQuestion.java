package org.depromeet.sambad.moring.meeting.question.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.presentation.exception.FinishedMeetingQuestionException;
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

	public static final int RESPONSE_TIME_LIMIT_HOURS = 4;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meeting_id")
	private Meeting meeting;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meeting_member_id")
	private MeetingMember targetMember;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id")
	private Question question;

	@OneToMany(mappedBy = "meetingQuestion", fetch = FetchType.LAZY)
	private List<MeetingAnswer> memberAnswers = new ArrayList<>();

	private LocalDateTime startTime;

	@Builder
	public MeetingQuestion(Meeting meeting, MeetingMember targetMember, Question question, LocalDateTime now) {
		this.meeting = meeting;
		this.targetMember = targetMember;
		this.question = question;
		this.startTime = now;
	}

	public int getResponseCount() {
		return this.memberAnswers.size();
	}

	public String getQuestionImageUrl() {
		return question.getQuestionImageUrl();
	}

	public String getTitle() {
		return question.getTitle();
	}

	public LocalDate getStartDate() {
		return startTime.toLocalDate();
	}

	public void validateNotFinished(LocalDateTime now) {
		LocalDateTime endTime = startTime.plusHours(RESPONSE_TIME_LIMIT_HOURS);
		if (now.isAfter(endTime)) {
			throw new FinishedMeetingQuestionException();
		}
	}
}
