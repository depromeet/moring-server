package org.depromeet.sambad.moyeo.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

	private final ExceptionCode code;

	public BusinessException(ExceptionCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
