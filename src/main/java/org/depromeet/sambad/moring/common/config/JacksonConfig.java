package org.depromeet.sambad.moring.common.config;

import org.depromeet.sambad.moring.file.presentation.annotation.FullFileUrlAnnotationIntrospector;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer customizer(
		FullFileUrlAnnotationIntrospector fullFileUrlAnnotationIntrospector
	) {
		return builder -> builder.annotationIntrospector(fullFileUrlAnnotationIntrospector);
	}
}
