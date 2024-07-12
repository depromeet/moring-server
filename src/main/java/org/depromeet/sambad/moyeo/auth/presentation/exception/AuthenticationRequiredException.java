package org.depromeet.sambad.moyeo.auth.presentation.exception;

import static org.depromeet.sambad.moyeo.auth.presentation.exception.AuthExceptionCode.AUTHENTICATION_REQUIRED;

/**
 * Auth 관련 Exception은 AuthenticationException을 상속받아야 한다.<br />
 * AuthenticationEntryPoint를 통한 예외 처리가 수행되어야 하기 때문이다.
 */
public class AuthenticationRequiredException extends BusinessAuthException {
	public AuthenticationRequiredException() {
		super(AUTHENTICATION_REQUIRED);
	}
}
