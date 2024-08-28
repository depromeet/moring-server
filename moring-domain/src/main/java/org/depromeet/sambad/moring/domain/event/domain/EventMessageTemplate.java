package org.depromeet.sambad.moring.domain.event.domain;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class EventMessageTemplate {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "event_template_id")
	private Long id;

	@Enumerated(STRING)
	@Column(columnDefinition = "varchar(50)")
	private EventType type;

	private String template;

	public String replaceTemplateVariables(Map<String, String> contentsMap) {
		if (contentsMap == null) {
			return template;
		}

		String message = template;
		for (String key : contentsMap.keySet()) {
			String regex = Pattern.quote("#{" + key + "}");
			message = message.replaceAll(regex, Matcher.quoteReplacement(contentsMap.get(key)));
		}
		return message;
	}
}
