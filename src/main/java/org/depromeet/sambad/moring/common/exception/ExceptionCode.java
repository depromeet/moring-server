package org.depromeet.sambad.moring.common.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {

	HttpStatus getStatus();

	String getCode();

	String getMessage();
}
