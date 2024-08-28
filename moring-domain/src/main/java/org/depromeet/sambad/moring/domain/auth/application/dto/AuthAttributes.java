package org.depromeet.sambad.moring.domain.auth.application.dto;

import java.util.Map;

import org.depromeet.sambad.moring.domain.user.domain.LoginProvider;

public interface AuthAttributes {

	String getExternalId();

	String getEmail();

	String getName();

	String getProfileImageUrl();

	LoginProvider getProvider();

	boolean hasDefaultProfileImage();

	static AuthAttributes of(String providerId, Map<String, Object> attributes) {
		if (LoginProvider.kakao.isProviderOf(providerId)) {
			return KakaoAuthAttributes.of(attributes);
		}

		throw new IllegalArgumentException("Unsupported id: " + providerId);
	}
}
