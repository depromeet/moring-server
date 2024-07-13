package org.depromeet.sambad.moyeo.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.depromeet.sambad.moyeo.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moyeo.file.domain.FileEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "image_file_id")
	@OneToOne(fetch = FetchType.LAZY)
	private FileEntity imageFile;

	private String name;

	private String email;

	@Enumerated(EnumType.STRING)
	private LoginProvider loginProvider;

	public static User of(FileEntity imageFile, String name, String email, LoginProvider loginProvider) {
		return new User(imageFile, name, email, loginProvider);
	}

	private User(FileEntity imageFile, String name, String email, LoginProvider loginProvider) {
		this.imageFile = imageFile;
		this.name = name;
		this.email = email;
		this.loginProvider = loginProvider;
	}

	public String getProfileImageUrl() {
		return imageFile != null ? imageFile.getPhysicalPath() : null;
	}
}
