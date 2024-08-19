package org.depromeet.sambad.moring.common.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

	private final ExceptionCode code;

	public BusinessException(ExceptionCode code) {
		super(code.getMessage());
		this.code = code;
	}

	public boolean isServerError() {
		return code.getStatus().equals(INTERNAL_SERVER_ERROR);
	}
}
