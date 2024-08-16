package org.depromeet.sambad.moring.meeting.question.domain;

import static org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestionStatus.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.presentation.exception.DuplicateMeetingQuestionException;
import org.depromeet.sambad.moring.meeting.question.presentation.exception.FinishedMeetingQuestionException;
import org.depromeet.sambad.moring.meeting.question.presentation.exception.InvalidMeetingMemberTargetException;
import org.depromeet.sambad.moring.question.domain.Question;
import org.depromeet.sambad.moring.question.domain.QuestionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	public static final int RESPONSE_TIME_LIMIT_SECONDS = 4 * 60 * 60;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meeting_question_id")
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

	@Enumerated(EnumType.STRING)
	private MeetingQuestionStatus status;

	private LocalDateTime startTime;

	private LocalDateTime expiredAt;

	@OneToMany(mappedBy = "meetingQuestion", fetch = FetchType.LAZY)
	private List<MeetingAnswer> memberAnswers = new ArrayList<>();

	@Builder
	private MeetingQuestion(
		Meeting meeting,
		MeetingMember targetMember,
		Question question,
		LocalDateTime now,
		MeetingQuestionStatus status,
		LocalDateTime expiredAt
	) {
		this.meeting = meeting;
		this.targetMember = targetMember;
		this.question = question;
		this.startTime = now;
		this.status = status;
		this.expiredAt = expiredAt;

		meeting.addMeetingQuestion(this);
		targetMember.addMeetingQuestion(this);
		if (question != null) {
			question.addMeetingQuestion(this);
		}
	}

	public static MeetingQuestion createActiveMeetingQuestion(
		Meeting meeting, MeetingMember targetMember, Question activeQuestion, LocalDateTime now
	) {
		LocalDateTime expiredAt = now.plusSeconds(RESPONSE_TIME_LIMIT_SECONDS);
		return new MeetingQuestion(meeting, targetMember, activeQuestion, now, ACTIVE, expiredAt);
	}

	public static MeetingQuestion createNextMeetingQuestion(
		Meeting meeting, MeetingMember targetMember, LocalDateTime startTime
	) {
		LocalDateTime expiredAt = startTime.plusSeconds(RESPONSE_TIME_LIMIT_SECONDS);
		return new MeetingQuestion(meeting, targetMember, null, startTime, NOT_STARTED, expiredAt);
	}

	public void addMeetingAnswer(MeetingAnswer meetingAnswer) {
		this.memberAnswers.add(meetingAnswer);
	}

	public void registerQuestion(MeetingMember targetMember, Question question) {
		validateTarget(targetMember);
		if (this.question != null) {
			throw new DuplicateMeetingQuestionException();
		}
		this.question = question;
		this.status = ACTIVE;
		this.question.addMeetingQuestion(this);
	}

	public void updateStatusToInactive() {
		this.status = MeetingQuestionStatus.INACTIVE;
	}

	public void updateStatusToActive(LocalDateTime startTime) {
		if (this.status != NOT_STARTED) {
			throw new IllegalStateException("이미 시작되거나 종료된 질문입니다.");
		}

		this.startTime = startTime;
		this.expiredAt = startTime.plusSeconds(RESPONSE_TIME_LIMIT_SECONDS);
		this.status = ACTIVE;
	}

	public int getResponseCount() {
		return this.memberAnswers.stream()
			.map(MeetingAnswer::getMeetingMember)
			.map(MeetingMember::getId)
			.distinct()
			.toList().size();
	}

	public String getQuestionImageUrl() {
		return question.getQuestionImageUrl();
	}

	public String getTitle() {
		return question.getTitle();
	}

	public LocalDateTime getNextStartTime() {
		return startTime.plusSeconds(RESPONSE_TIME_LIMIT_SECONDS);
	}

	public void validateNotFinished(LocalDateTime now) {
		LocalDateTime endTime = startTime.plusSeconds(RESPONSE_TIME_LIMIT_SECONDS);
		if (now.isAfter(endTime)) {
			throw new FinishedMeetingQuestionException();
		}
	}

	public void validateMeetingAnswerCount(int answerSize) {
		QuestionType.validateAnswerCount(question.getQuestionType(), answerSize);
	}

	public Long getEpochMilliStartTime() {
		return startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	private void validateTarget(MeetingMember targetMember) {
		if (!this.targetMember.equals(targetMember)) {
			throw new InvalidMeetingMemberTargetException();
		}
	}
}
