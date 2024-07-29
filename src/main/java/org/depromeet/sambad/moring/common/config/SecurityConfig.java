package org.depromeet.sambad.moring.common.config;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

import java.util.List;

import org.depromeet.sambad.moring.auth.infrastructure.SecurityProperties;
import org.depromeet.sambad.moring.auth.presentation.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final DefaultOAuth2UserService defaultOAuth2UserService;
	private final AuthenticationEntryPoint authenticationEntryPoint;
	private final JwtTokenFilter jwtTokenFilter;
	private final SecurityProperties securityProperties;
	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	private final AuthenticationFailureHandler authenticationFailureHandler;

	private static final String[] PERMIT_ALL_PATTERNS = {
		"/swagger-ui/**",
		"/actuator/health",
		"/v3/api-docs/**",
		"/login/**",
		"/oauth2/**",
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		disableSecurityBasic(httpSecurity);
		configureCorsPolicy(httpSecurity);
		configureSessionManagement(httpSecurity);
		configureApiAuthorization(httpSecurity);
		configureContentSecurityPolicy(httpSecurity);
		configureOAuth2Login(httpSecurity);
		configureExceptionHandler(httpSecurity);

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

	private void configureCorsPolicy(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors(cors -> cors.configurationSource(request -> {
			var corsConfiguration = new CorsConfiguration();
			corsConfiguration.setAllowedOrigins(List.of(
				"http://localhost:8080", "http://localhost:3000", "http://local.moring.one:3000"));
			corsConfiguration.setAllowedOriginPatterns(List.of("https://*.moring.one"));
			corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS"));
			corsConfiguration.setAllowedHeaders(List.of("*"));
			corsConfiguration.setAllowCredentials(true);
			return corsConfiguration;
		}));
	}

	private void configureApiAuthorization(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(authorize ->
			authorize.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
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

	private void configureOAuth2Login(HttpSecurity http) throws Exception {
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		http.oauth2Login(oauth2 ->
			oauth2.loginPage(securityProperties.loginUrl())
				.userInfoEndpoint(userInfo -> userInfo.userService(defaultOAuth2UserService))
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler)
		);
	}

	private void configureExceptionHandler(HttpSecurity http) throws Exception {
		http.exceptionHandling(exceptionHandler ->
			exceptionHandler.authenticationEntryPoint(authenticationEntryPoint));
	}
}
