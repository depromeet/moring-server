package org.depromeet.sambad.moyeo.auth.application.dto;

import org.depromeet.sambad.moyeo.user.domain.LoginProvider;

import java.util.Map;

public interface AuthAttributes {

	String getExternalId();

	String getEmail();

	String getName();

	String getProfileImageUrl();

	LoginProvider getProvider();

	static AuthAttributes of(String providerId, Map<String, Object> attributes) {
		if (LoginProvider.kakao.isProviderOf(providerId)) {
			return KakaoAuthAttributes.of(attributes);
		}

		throw new IllegalArgumentException("Unsupported id: " + providerId);
	}
}
