package org.depromeet.sambad.moring.domain.auth.application.dto;

import org.depromeet.sambad.moring.domain.user.domain.LoginProvider;

public class MockAuthAttributes implements AuthAttributes {
	@Override
	public String getExternalId() {
		return "test";
	}

	@Override
	public String getEmail() {
		return "test@kakao.com";
	}

	@Override
	public String getName() {
		return "test";
	}

	@Override
	public String getProfileImageUrl() {
		return null;
	}

	@Override
	public LoginProvider getProvider() {
		return LoginProvider.kakao;
	}

	@Override
	public boolean hasDefaultProfileImage() {
		return false;
	}

	public static MockAuthAttributes of() {
		return new MockAuthAttributes();
	}
}
