package org.depromeet.sambad.moring.file.presentation.annotation;

import static java.lang.String.*;

import java.io.IOException;

import org.depromeet.sambad.moring.file.infrastructure.FileProperties;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class FullFileUrlSerializer extends JsonSerializer<String> {

	private final FileProperties fileProperties;

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (value == null) {
			gen.writeNull();
			return;
		}

		String fullUrl = format("%s/%s", fileProperties.baseUrl(), normalizePath(value));
		gen.writeString(fullUrl);
	}

	private String normalizePath(String value) {
		if (value.startsWith(".")) {
			value = value.substring(1);
		}

		if (value.startsWith("/")) {
			value = value.substring(1);
		}

		if (value.endsWith("/")) {
			value = value.substring(0, value.length() - 1);
		}

		return value;
	}
}

