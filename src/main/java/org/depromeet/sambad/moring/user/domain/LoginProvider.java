package org.depromeet.sambad.moring.user.domain;

import java.util.Objects;

public enum LoginProvider {
	kakao;

	public boolean isProviderOf(String providerId) {
		return Objects.equals(this.name(), providerId);
	}
}
