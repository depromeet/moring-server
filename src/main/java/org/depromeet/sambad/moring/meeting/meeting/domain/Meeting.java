package org.depromeet.sambad.moring.meeting.meeting.domain;

import static org.depromeet.sambad.moring.common.exception.GlobalExceptionCode.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.common.exception.BusinessException;
import org.depromeet.sambad.moring.common.logging.LoggingUtils;
import org.depromeet.sambad.moring.meeting.meeting.presentation.request.MeetingPersistRequest;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meeting_id")
	private Long id;

	private String name;

	@Embedded
	private MeetingCode code;

	@OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
	private List<MeetingMember> meetingMembers = new ArrayList<>();

	@OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
	private List<TypesPerMeeting> typesPerMeetings = new ArrayList<>();

	@OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
	private List<MeetingQuestion> meetingQuestions = new ArrayList<>();

	private Meeting(String name, MeetingCode code) {
		this.name = name;
		this.code = code;
	}

	public static Meeting of(MeetingPersistRequest request, MeetingCode meetingCode) {
		return new Meeting(request.name(), meetingCode);
	}

	public String getCode() {
		return Optional.ofNullable(code)
			.map(MeetingCode::getCode)
			.orElseThrow(() -> new IllegalStateException("모임 코드가 존재하지 않습니다."));
	}

	public MeetingMember getOwner() {
		return meetingMembers
			.stream()
			.filter(MeetingMember::isOwner)
			.findAny()
			.orElseThrow(() -> {
				LoggingUtils.error("Meeting PK : " + id + " 에 OWNER 가 존재하지 않습니다.");
				throw new BusinessException(SERVER_ERROR);
			});
	}

	public int getTotalMemberCount() {
		return meetingMembers.size();
	}

	public int getQuestionNumber(MeetingQuestion meetingQuestion) {
		return meetingQuestions.indexOf(meetingQuestion) + 1;
	}

	public double calculateEngagementRate(MeetingQuestion meetingQuestion) {
		if (getTotalMemberCount() == 0)
			return 0;

		return ((double)meetingQuestion.getResponseCount() / getTotalMemberCount()) * 100;
	}
}
