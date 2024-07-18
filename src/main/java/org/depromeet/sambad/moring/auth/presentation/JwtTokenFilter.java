package org.depromeet.sambad.moring.auth.presentation;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.depromeet.sambad.moring.auth.domain.TokenResolver;
import org.depromeet.sambad.moring.auth.presentation.exception.AuthenticationRequiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.ACCESS_TOKEN;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private final TokenResolver tokenResolver;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
	) throws ServletException, IOException {
		processTokenAuthentication(request, response);
		filterChain.doFilter(request, response);
	}

	private void processTokenAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {
			String token = resolveTokenFromRequest(request);
			setAuthentication(request, getUserDetails(token));
		} catch (ExpiredJwtException | AuthenticationRequiredException e) {
			log.debug("Failed to authenticate", e);
			invalidateCookie(response);
		} catch (JwtException e) {
			log.warn("Failed to authenticate", e);
			invalidateCookie(response);
		} catch (Exception e) {
			log.error("Failed to authenticate", e);
			invalidateCookie(response);
		}
	}

	private String resolveTokenFromRequest(HttpServletRequest request) {
		return tokenResolver.resolveTokenFromRequest(request)
				.orElseThrow(AuthenticationRequiredException::new);
	}

	private UserDetails getUserDetails(String token) {
		String subject = tokenResolver.getSubjectFromToken(token);
		return userDetailsService.loadUserByUsername(subject);
	}

	private void setAuthentication(HttpServletRequest request, UserDetails userDetails) {
		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private void invalidateCookie(HttpServletResponse response) {
		Cookie cookieForInvalidate = new Cookie(ACCESS_TOKEN, null);
		cookieForInvalidate.setMaxAge(0);
		response.addCookie(cookieForInvalidate);

		SecurityContextHolder.clearContext();
	}
}
