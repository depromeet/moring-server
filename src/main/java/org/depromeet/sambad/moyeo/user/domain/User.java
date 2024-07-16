package org.depromeet.sambad.moyeo.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.depromeet.sambad.moyeo.auth.application.dto.AuthAttributes;
import org.depromeet.sambad.moyeo.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moyeo.file.domain.FileEntity;

import java.util.Objects;
import java.util.Optional;

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

    private String externalId;

    private User(FileEntity imageFile, String name, String email, LoginProvider loginProvider, String externalId) {
        this.imageFile = imageFile;
        this.name = name;
        this.email = email;
        this.loginProvider = loginProvider;
        this.externalId = externalId;
    }

    public static User from(
            FileEntity imageFile, AuthAttributes authAttributes
    ) {
        return new User(imageFile, authAttributes.getName(), authAttributes.getEmail(), authAttributes.getProvider(),
                authAttributes.getExternalId());
    }

    public String getProfileImageUrl() {
        return Optional.ofNullable(imageFile)
                .map(FileEntity::getPhysicalPath)
                .orElse(null);
    }

    /**
     * 동일한 이메일이지만 다른 제공자로 가입한 사용자인지 확인합니다.
     */
    public boolean hasDifferentProviderWithEmail(String email, String externalId) {
        return Objects.equals(this.email, email) && !Objects.equals(this.externalId, externalId);
    }
}
