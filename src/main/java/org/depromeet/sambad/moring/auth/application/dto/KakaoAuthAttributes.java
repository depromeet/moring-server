package org.depromeet.sambad.moring.auth.application.dto;

import static org.depromeet.sambad.moring.user.domain.LoginProvider.*;

import java.util.Map;

import org.depromeet.sambad.moring.user.domain.LoginProvider;
import org.springframework.util.StringUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * 카카오로부터 받은 인증 정보를 파싱하여 전달합니다.<br />
 * AuthAttributes를 구현한 각 클래스는 dirty code를 어느 정도 허용합니다.<br />
 * 각기 다른 소셜 로그인 서비스의 JSON 구조를 파싱하기 위해 굳은 일을 도맡아 합니다.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoAuthAttributes implements AuthAttributes {

	public static final String DEFAULT_PROFILE_IMAGE_PREFIX = "default_profile";

	private final String id;
	private final String email;
	private final String name;
	private final String profileImageUrl;
	private final LoginProvider provide;

	public static KakaoAuthAttributes of(Map<String, Object> attributes) {
		Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
		Map<String, Object> profile = (Map<String, Object>)kakaoAccount.get("profile");

		return new KakaoAuthAttributes(
			attributes.get("id").toString(),
			(String)kakaoAccount.get("email"),
			(String)profile.get("nickname"),
			(String)profile.get("profile_image_url"),
			kakao
		);
	}

	@Override
	public String getExternalId() {
		return this.id;
	}

	@Override
	public String getEmail() {
		return this.email;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getProfileImageUrl() {
		return this.profileImageUrl;
	}

	@Override
	public LoginProvider getProvider() {
		return this.provide;
	}

	@Override
	public boolean hasDefaultProfileImage() {
		return !StringUtils.hasText(this.profileImageUrl) ||
			this.profileImageUrl.contains(DEFAULT_PROFILE_IMAGE_PREFIX);
	}
}
