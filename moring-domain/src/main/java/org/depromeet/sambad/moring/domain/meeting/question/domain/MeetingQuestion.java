package org.depromeet.sambad.moring.domain.meeting.question.domain;

import static java.time.LocalDateTime.*;
import static org.depromeet.sambad.moring.domain.meeting.question.domain.MeetingQuestionStatus.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.depromeet.sambad.moring.domain.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.domain.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.domain.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.domain.meeting.question.presentation.exception.DuplicateMeetingQuestionException;
import org.depromeet.sambad.moring.domain.meeting.question.presentation.exception.FinishedMeetingQuestionException;
import org.depromeet.sambad.moring.domain.meeting.question.presentation.exception.InvalidMeetingMemberTargetException;
import org.depromeet.sambad.moring.domain.question.domain.Question;
import org.depromeet.sambad.moring.domain.question.domain.QuestionType;
import org.depromeet.sambad.moring.globalutils.logging.LoggingUtils;

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

	private int totalMemberCount; // 모임 질문 종료 시점의 모임원 수

	@OneToMany(mappedBy = "meetingQuestion", fetch = FetchType.LAZY)
	private List<MeetingAnswer> memberAnswers = new ArrayList<>();

	@Builder
	private MeetingQuestion(
		Meeting meeting,
		MeetingMember targetMember,
		Question question,
		LocalDateTime now,
		MeetingQuestionStatus status,
		LocalDateTime expiredAt,
		int totalMemberCount
	) {
		this.meeting = meeting;
		this.targetMember = targetMember;
		this.question = question;
		this.startTime = now;
		this.status = status;
		this.expiredAt = expiredAt;
		this.totalMemberCount = totalMemberCount;

		meeting.addMeetingQuestion(this);
		targetMember.addMeetingQuestion(this);
		if (question != null) {
			question.addMeetingQuestion(this);
		}
	}

	public static MeetingQuestion createActiveMeetingQuestion(
		Meeting meeting, MeetingMember targetMember, Question activeQuestion, LocalDateTime now, int totalMemberCount
	) {
		LocalDateTime expiredAt = now.plusSeconds(RESPONSE_TIME_LIMIT_SECONDS);
		return new MeetingQuestion(meeting, targetMember, activeQuestion, now, ACTIVE, expiredAt, totalMemberCount);
	}

	public static MeetingQuestion createNextMeetingQuestion(
		Meeting meeting, MeetingMember targetMember, LocalDateTime startTime, int totalMemberCount
	) {
		LocalDateTime expiredAt = startTime.plusSeconds(RESPONSE_TIME_LIMIT_SECONDS);
		return new MeetingQuestion(meeting, targetMember, null, startTime, NOT_STARTED, expiredAt,
			totalMemberCount);
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
		this.question.addMeetingQuestion(this);
	}

	public void updateStatusToInactive() {
		if (this.status == INACTIVE) {
			LoggingUtils.error("다음 MeetingQuestion 의 비활성화를 시도하였으나, 이미 비활성화된 질문입니다. meetingQuestionId : " + id);
			return;
		}
		this.status = MeetingQuestionStatus.INACTIVE;
		this.totalMemberCount = meeting.getTotalMemberCount();
	}

	public void updateStatusToActive(LocalDateTime startTime) {
		if (this.status != NOT_STARTED) {
			LoggingUtils.error("다음 MeetingQuestion 의 활성화를 시도하였으나, 이미 시작되거나 종료된 질문입니다. meetingQuestionId : "
				+ id + " status : " + status.name());
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

	public double calculateEngagementRate() {
		Integer totalMemberCount = (status == INACTIVE) ? this.totalMemberCount : this.meeting.getTotalMemberCount();
		if (totalMemberCount == 0)
			return 0;
		double engagementRate = ((double)getResponseCount() / totalMemberCount) * 100;
		return Math.round(engagementRate);
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

	public void replaceTargetMember(MeetingMember targetMember) {
		this.targetMember = targetMember;
		this.status = ACTIVE;
		this.question = null;

		LocalDateTime now = now();
		this.startTime = now;
		this.expiredAt = now.plusSeconds(RESPONSE_TIME_LIMIT_SECONDS);
	}

	private void validateTarget(MeetingMember targetMember) {
		if (!this.targetMember.equals(targetMember)) {
			throw new InvalidMeetingMemberTargetException();
		}
	}

	public String getFullTitle() {
		return question.getFullTitle();
	}
}
