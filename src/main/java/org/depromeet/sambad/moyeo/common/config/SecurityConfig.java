package org.depromeet.sambad.moyeo.common.config;

import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moyeo.auth.presentation.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final DefaultOAuth2UserService defaultOAuth2UserService;
	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	private final AuthenticationEntryPoint authenticationEntryPoint;
	private final JwtTokenFilter jwtTokenFilter;

	private static final String[] PERMIT_ALL_PATTERNS = {
			"/swagger-ui/**",
			"/actuator/health",
			"/login/**",
			"/oauth2/**",
	};


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		disableSecurityBasic(httpSecurity);
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
		http.oauth2Login(oauth2 ->
				oauth2.loginPage("/login")
						.userInfoEndpoint(userInfo -> userInfo.userService(defaultOAuth2UserService))
						.successHandler(authenticationSuccessHandler)
		);
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

	private void configureExceptionHandler(HttpSecurity http) throws Exception {
		http.exceptionHandling(exceptionHandler ->
				exceptionHandler.authenticationEntryPoint(authenticationEntryPoint));
	}
}
