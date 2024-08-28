package org.depromeet.sambad.moring.domain.auth.domain;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import java.util.Objects;

import org.depromeet.sambad.moring.domain.auth.presentation.exception.RefreshTokenNotValidaException;
import org.depromeet.sambad.moring.domain.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class RefreshToken extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "refresh_token_id")
	private Long id;

	private Long userId;

	private String token;

	private LocalDateTime expiredAt;

	private RefreshToken(Long userId, String token, LocalDateTime expiredAt) {
		this.userId = userId;
		this.token = token;
		this.expiredAt = expiredAt;
	}

	public static RefreshToken of(Long userId, String token, long expiredSeconds) {
		LocalDateTime expiredAt = LocalDateTime.now().plusSeconds(expiredSeconds);

		return new RefreshToken(userId, token, expiredAt);
	}

	public void rotate(String token) {
		this.token = token;
	}

	public void updateExpirationIfExpired(long expiredSeconds) {
		if (expiredAt.isBefore(LocalDateTime.now())) {
			expiredAt = LocalDateTime.now().plusSeconds(expiredSeconds);
		}
	}

	public void validateWith(String token, Long userId) {
		if (isNotMatchedToken(token) || isExpired() || isNotMatchedUserId(userId)) {
			throw new RefreshTokenNotValidaException();
		}
	}

	private boolean isNotMatchedToken(String token) {
		return !Objects.equals(this.token, token);
	}

	private boolean isExpired() {
		return expiredAt.isBefore(LocalDateTime.now());
	}

	private boolean isNotMatchedUserId(Long userId) {
		return !Objects.equals(this.userId, userId);
	}
}
