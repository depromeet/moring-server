package org.depromeet.sambad.moring.domain.meeting.member.domain;

import static jakarta.persistence.EnumType.*;
import static org.depromeet.sambad.moring.domain.common.utils.UserIdResolver.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.depromeet.sambad.moring.domain.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.domain.common.domain.Gender;
import org.depromeet.sambad.moring.domain.file.domain.FileEntity;
import org.depromeet.sambad.moring.domain.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.domain.meeting.member.presentation.request.MeetingMemberPersistRequest;
import org.depromeet.sambad.moring.domain.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.domain.meeting.question.presentation.exception.InvalidMeetingMemberNextTargetException;
import org.depromeet.sambad.moring.domain.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingMember extends BaseTimeEntity implements Comparable<MeetingMember> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meeting_member_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meeting_id")
	private Meeting meeting;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_image_file_id")
	private FileEntity profileImageFile;

	@Enumerated(STRING)
	@Column(columnDefinition = "varchar(20)")
	private MeetingMemberRole role;

	private String name;

	@Enumerated(STRING)
	@Column(columnDefinition = "varchar(10)")
	private Gender gender;

	private LocalDate birth;

	private String job;

	private String location;

	@Enumerated(STRING)
	@Column(columnDefinition = "varchar(4)")
	private MBTI mbti;

	private String introduction;

	@OneToMany(mappedBy = "targetMember", fetch = FetchType.LAZY)
	private List<MeetingQuestion> meetingQuestions = new ArrayList<>();

	@OneToMany(mappedBy = "meetingMember", fetch = FetchType.LAZY)
	private List<MeetingMemberHobby> meetingMemberHobbies = new ArrayList<>();

	private MeetingMember(Meeting meeting, User user, FileEntity profileImageFile, MeetingMemberRole role, String name,
		Gender gender, LocalDate birth, String job, String location, MBTI mbti, String introduction) {
		this.meeting = meeting;
		this.user = user;
		this.profileImageFile = profileImageFile;
		this.role = role;
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.job = job;
		this.location = location;
		this.mbti = mbti;
		this.introduction = introduction;
	}

	public static MeetingMember createMemberWith(
		Meeting meeting, User user, MeetingMemberPersistRequest request
	) {
		return new MeetingMember(
			meeting,
			user,
			user.getProfileImageFile(),
			request.role() == null ? MeetingMemberRole.MEMBER : request.role(),
			request.name(),
			request.gender(),
			request.birth(),
			request.job(),
			request.location(),
			request.mbti(),
			request.introduction());
	}

	public void addMeetingQuestion(MeetingQuestion meetingQuestion) {
		this.meetingQuestions.add(meetingQuestion);
	}

	public String getProfileImageUrl() {
		return Optional.ofNullable(this.profileImageFile)
			.map(FileEntity::getPhysicalPath)
			.orElse(null);
	}

	public List<Hobby> getHobbies() {
		return meetingMemberHobbies.stream()
			.map(MeetingMemberHobby::getHobby)
			.toList();
	}

	public List<String> getHobbiesAsString() {
		return meetingMemberHobbies.stream()
			.map(MeetingMemberHobby::getHobbyContent)
			.toList();
	}

	public boolean isOtherMeeting(MeetingMember targetMember) {
		return !Objects.equals(this.meeting, targetMember.getMeeting());
	}

	public boolean isOwner() {
		return role.equals(MeetingMemberRole.OWNER);
	}

	public boolean isMe() {
		return Objects.equals(this.user.getId(), resolveRequestedUserId());
	}

	@Override
	public int compareTo(MeetingMember o) {
		// 지연로딩 객체일 경우, getter를 통해 필드를 가져와야 프록시 객체가 초기화되어 정상적인 비교 수행 가능
		// OWNER 역할을 가진 멤버가 우선순위를 가짐
		if (this.role == MeetingMemberRole.OWNER && o.getRole() != MeetingMemberRole.OWNER) {
			return -1;
		} else if (this.role != MeetingMemberRole.OWNER && o.getRole() == MeetingMemberRole.OWNER) {
			return 1;
		}

		// 둘 다 OWNER 역할이 아니거나 둘 다 OWNER 역할일 때 이름순으로 정렬
		return this.name.compareTo(o.getName());
	}

	public void validateNextTarget(MeetingMember targetMember) {
		if (isOtherMeeting(targetMember) || Objects.equals(this.user, targetMember.getUser())) {
			throw new InvalidMeetingMemberNextTargetException();
		}
	}

	public boolean isNotEqualMemberWith(MeetingMember meetingMember) {
		return !Objects.equals(this.getId(), meetingMember.getId());
	}

	public void update(MeetingMemberPersistRequest request) {
		this.name = request.name();
		this.gender = request.gender();
		this.birth = request.birth();
		this.job = request.job();
		this.location = request.location();
		this.mbti = request.mbti();
		this.introduction = request.introduction();
	}

	public static Long getLastMeetingId(List<MeetingMember> meetingMembers) {
		return meetingMembers.stream()
			.findFirst()
			.map(MeetingMember::getUser)
			.map(User::getLastMeetingId)
			.orElse(null);
	}
}
