package org.depromeet.sambad.moring.domain.auth.presentation.exception;

import static jakarta.servlet.http.HttpServletResponse.*;
import static org.depromeet.sambad.moring.domain.auth.presentation.exception.AuthExceptionCode.*;

import java.io.IOException;

import org.depromeet.sambad.moring.domain.common.exception.ExceptionResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Security Filter에서 발생한 예외는, 모두 공통된 status 및 error response body를 반환합니다.<br />
 * 로그인 실패에 대한 상세 정보를 제공하는 것은 안전하지 않습니다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(
		HttpServletRequest request, HttpServletResponse response, AuthenticationException exception
	) throws IOException {
		setResponseBodyBasicInfo(response);
		objectMapper.writeValue(response.getOutputStream(), ExceptionResponse.from(AUTHENTICATION_REQUIRED));
	}

	private void setResponseBodyBasicInfo(HttpServletResponse response) {
		response.setStatus(SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
	}
}

