package org.depromeet.sambad.moyeo.common.config;

import org.depromeet.sambad.moyeo.auth.application.OAuth2LoginSuccessHandler;
import org.depromeet.sambad.moyeo.auth.application.OAuth2UserService;
import org.depromeet.sambad.moyeo.auth.presentation.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final JwtTokenFilter jwtTokenFilter;

    private static final String[] PERMIT_ALL_PATTERNS = {
            "/swagger-ui/**",
            "/actuator/health",
            "/login/**",
            "/oauth2/**",
    };

    public SecurityConfig(OAuth2UserService oAuth2UserService, OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler, JwtTokenFilter jwtTokenFilter) {
        this.oAuth2UserService = oAuth2UserService;
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        disableSecurityBasic(httpSecurity);
        configureSessionManagement(httpSecurity);
        configureApiAuthorization(httpSecurity);
        configureContentSecurityPolicy(httpSecurity);
        configureOauth2Login(httpSecurity);

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

    private void configureOauth2Login(HttpSecurity http) throws Exception {
        http.oauth2Login(oauth2 ->
                oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(oAuth2UserService)
                        )
                        .successHandler(oAuth2LoginSuccessHandler)
        );
        http.addFilterBefore(jwtTokenFilter, OAuth2LoginAuthenticationFilter.class);
    }
}
