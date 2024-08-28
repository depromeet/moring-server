package org.depromeet.sambad.moring.infra.config;

import java.util.List;

import org.depromeet.sambad.moring.infra.MoringConfig;
import org.depromeet.sambad.moring.infra.resolver.UserIdArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class UserIdArgumentResolverConfig implements WebMvcConfigurer, MoringConfig {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new UserIdArgumentResolver());
	}
}
