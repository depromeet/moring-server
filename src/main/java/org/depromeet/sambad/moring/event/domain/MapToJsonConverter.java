package org.depromeet.sambad.moring.event.domain;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MapToJsonConverter implements AttributeConverter<Map<String, Object>, String> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(Map<String, Object> attribute) {
		if (attribute == null) {
			return null;
		}
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Could not convert map to JSON", e);
		}
	}

	@Override
	public Map<String, Object> convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		try {
			return objectMapper.readValue(dbData, HashMap.class);
		} catch (IOException e) {
			throw new IllegalArgumentException("Could not convert JSON to map", e);
		}
	}
}
