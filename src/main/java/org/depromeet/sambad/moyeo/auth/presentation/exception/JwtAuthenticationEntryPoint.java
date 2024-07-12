package org.depromeet.sambad.moyeo.auth.presentation.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.depromeet.sambad.moyeo.common.exception.ExceptionCode;
import org.depromeet.sambad.moyeo.common.exception.ExceptionResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.depromeet.sambad.moyeo.common.exception.GlobalExceptionCode.SERVER_ERROR;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(
			HttpServletRequest request, HttpServletResponse response, AuthenticationException exception
	) throws IOException {
		ExceptionCode code = judgeStatusCode(exception);

		setResponseBodyBasicInfo(response, code);

		objectMapper.writeValue(response.getOutputStream(), ExceptionResponse.from(code));
	}

	private void setResponseBodyBasicInfo(HttpServletResponse response, ExceptionCode code) {
		response.setStatus(code.getStatus().value());
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
	}

	private ExceptionCode judgeStatusCode(AuthenticationException ex) {
		return ex instanceof BusinessAuthException bex
				? bex.getCode()
				: SERVER_ERROR;
	}

}

