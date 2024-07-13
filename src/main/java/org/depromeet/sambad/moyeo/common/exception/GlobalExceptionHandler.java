package org.depromeet.sambad.moyeo.common.exception;

import static org.depromeet.sambad.moyeo.common.exception.GlobalExceptionCode.*;
import static org.depromeet.sambad.moyeo.file.presentation.exception.FileExceptionCode.*;

import java.util.Objects;

import org.depromeet.sambad.moyeo.common.logging.LoggingUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException exception) {
		ExceptionResponse response = ExceptionResponse.from(exception);
		return ResponseEntity.status(response.status()).body(response);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ExceptionResponse> handleException(Exception exception) {
		Sentry.captureException(exception);
		LoggingUtils.error(exception);

		return ResponseEntity.internalServerError().body(ExceptionResponse.from(SERVER_ERROR));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
		HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		int index = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage().indexOf(",");
		String message = Objects.requireNonNull(exception.getFieldError())
			.getDefaultMessage()
			.substring(index + 2);
		ExceptionResponse response = ExceptionResponse.of(INVALID_INPUT.getStatus(), INVALID_INPUT.getCode(), message);
		return ResponseEntity.status(response.status()).body(response);
	}

	@Override
	protected ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex,
		HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ExceptionResponse response = ExceptionResponse.from(EXCEED_FILE_SIZE);
		return ResponseEntity.status(response.status()).body(response);
	}
}