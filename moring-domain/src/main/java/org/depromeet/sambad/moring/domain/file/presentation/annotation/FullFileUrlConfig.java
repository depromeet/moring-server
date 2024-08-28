package org.depromeet.sambad.moring.domain.file.presentation.annotation;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FullFileUrlConfig {

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer customizer(
		FullFileUrlAnnotationIntrospector fullFileUrlAnnotationIntrospector
	) {
		return builder -> builder.annotationIntrospector(fullFileUrlAnnotationIntrospector);
	}
}
