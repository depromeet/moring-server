package org.depromeet.sambad.moyeo.common.config;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

@Configuration
public class SecurityConfig {

	private static final String[] PERMIT_ALL_PATTERNS = {
		"/swagger-ui/**",
		"/v3/api-docs/**",
		"/actuator/health",
		"/api/v1/object-storage/**"
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		disableSecurityBasic(httpSecurity);
		configureSessionManagement(httpSecurity);
		configureApiAuthorization(httpSecurity);
		configureContentSecurityPolicy(httpSecurity);

		return httpSecurity.build();
	}

	private void disableSecurityBasic(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable);
	}

	private void configureSessionManagement(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
	}

	private void configureApiAuthorization(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(authorize ->
			authorize
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
				.requestMatchers(PERMIT_ALL_PATTERNS).permitAll()
				.anyRequest().authenticated()
		);
	}

	private void configureContentSecurityPolicy(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.headers(headersConfig -> headersConfig.contentSecurityPolicy(
				cspConfig -> cspConfig.policyDirectives("script-src 'self'")
			));
	}
}
