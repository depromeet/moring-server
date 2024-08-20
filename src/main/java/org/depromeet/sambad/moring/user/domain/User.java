package org.depromeet.sambad.moring.user.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.depromeet.sambad.moring.auth.application.dto.AuthAttributes;
import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.file.domain.FileEntity;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@JoinColumn(name = "profile_image_file_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private FileEntity profileImageFile;

	private String name;

	private String email;

	@Enumerated(EnumType.STRING)
	private LoginProvider loginProvider;

	private String externalId;

	@Column(columnDefinition = "TINYINT")
	private Boolean onboardingCompleted;

	@OneToMany(mappedBy = "user")
	private List<MeetingMember> meetingMember = new ArrayList<>();

	private User(
		FileEntity imageFile, String name, String email, LoginProvider loginProvider, String externalId,
		boolean onboardingCompleted
	) {
		this.profileImageFile = imageFile;
		this.name = name;
		this.email = email;
		this.loginProvider = loginProvider;
		this.externalId = externalId;
		this.onboardingCompleted = onboardingCompleted;
	}

	public static User from(
		FileEntity imageFile, AuthAttributes authAttributes
	) {
		return new User(imageFile, authAttributes.getName(), authAttributes.getEmail(), authAttributes.getProvider(),
			authAttributes.getExternalId(), false);
	}

	public String getProfileImageFileUrl() {
		return Optional.ofNullable(profileImageFile)
			.map(FileEntity::getPhysicalPath)
			.orElse(null);
	}

	/**
	 * 동일한 이메일이지만 다른 제공자로 가입한 사용자인지 확인합니다.
	 */
	public boolean hasDifferentProviderWithEmail(String email, String externalId) {
		return Objects.equals(this.email, email) && !Objects.equals(this.externalId, externalId);
	}

	public void updateProfileImage(FileEntity fileEntity) {
		this.profileImageFile = fileEntity;
	}

	public void completeOnboarding() {
		this.onboardingCompleted = true;
	}

	public boolean isNotEnteredAnyMeeting() {
		return meetingMember.isEmpty();
	}
}
