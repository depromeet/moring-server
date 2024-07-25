
package org.depromeet.sambad.moring.meeting.member.domain;

import static jakarta.persistence.EnumType.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.common.domain.Gender;
import org.depromeet.sambad.moring.file.domain.FileEntity;
import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.member.presentation.request.MeetingMemberPersistRequest;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.exception.InvalidMeetingMemberTargetException;
import org.depromeet.sambad.moring.user.domain.User;

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
import jakarta.persistence.OneToOne;
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

	@OneToOne(fetch = FetchType.LAZY)
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
			request.role(),
			request.name(),
			request.gender(),
			request.birth(),
			request.job(),
			request.location(),
			request.mbti(),
			request.introduction());
	}

	public String getProfileImageUrl() {
		return Optional.ofNullable(this.profileImageFile)
			.map(FileEntity::getPhysicalPath)
			.orElse(null);
	}

	public List<String> getHobbies() {
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

	@Override
	public int compareTo(MeetingMember o) {
		// OWNER 역할을 가진 멤버가 우선순위를 가짐
		if (this.role == MeetingMemberRole.OWNER && o.role != MeetingMemberRole.OWNER) {
			return -1;
		} else if (this.role != MeetingMemberRole.OWNER && o.role == MeetingMemberRole.OWNER) {
			return 1;
		}

		// 둘 다 OWNER 역할이 아니거나 둘 다 OWNER 역할일 때 이름순으로 정렬
		return this.name.compareTo(o.name);
	}

	public void validateTargetMember(MeetingMember targetMember) {
		if (isOtherMeeting(targetMember) || Objects.equals(this.user, targetMember.getUser())) {
			throw new InvalidMeetingMemberTargetException();
		}
	}
}
