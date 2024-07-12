package org.depromeet.sambad.moyeo.auth.presentation.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class BusinessAuthException extends AuthenticationException {

	private final AuthExceptionCode code;

	public BusinessAuthException(AuthExceptionCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
