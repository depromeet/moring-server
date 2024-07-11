package org.depromeet.sambad.moyeo.user.domain;

import java.util.Objects;

public enum LoginProvider {
	kakao;

	public boolean isProviderOf(String provider) {
		return Objects.equals(this.name(), provider);
	}
}
