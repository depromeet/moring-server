package org.depromeet.sambad.moring.domain.file.presentation.annotation;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FullFileUrlAnnotationIntrospector extends JacksonAnnotationIntrospector {

	private final FullFileUrlSerializer fullFileUrlSerializer;

	@Override
	public JsonSerializer<?> findSerializer(Annotated am) {
		if (am.hasAnnotation(FullFileUrl.class)) {
			return fullFileUrlSerializer;
		}
		return (JsonSerializer<?>)super.findSerializer(am);
	}
}

