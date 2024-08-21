package org.depromeet.sambad.moring.event.domain;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static org.depromeet.sambad.moring.event.domain.EventStatus.*;
import static org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion.*;

import java.time.LocalDateTime;
import java.util.Map;

import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Event extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "event_id")
	private Long id;

	private Long userId;

	private Long meetingId;

	@Enumerated(STRING)
	private EventType type;

	@Enumerated(STRING)
	private EventStatus status;

	private String message;

	private LocalDateTime expiredAt;

	@Column(columnDefinition = "text")
	@Convert(converter = MapToJsonConverter.class)
	private Map<String, Object> additionalData = Map.of();

	private Event(
		Long userId, Long meetingId, EventType type, EventStatus status, String message, LocalDateTime expiredAt,
		Map<String, Object> additionalData
	) {
		this.userId = userId;
		this.meetingId = meetingId;
		this.type = type;
		this.status = status;
		this.message = message;
		this.expiredAt = expiredAt;
		this.additionalData = additionalData;
	}

	public static Event publish(
		Long userId, Long meetingId, EventType type, String message, Map<String, Object> additionalData
	) {
		LocalDateTime expiredAt = LocalDateTime.now().plusSeconds(RESPONSE_TIME_LIMIT_SECONDS);
		return new Event(userId, meetingId, type, ACTIVE, message, expiredAt, additionalData);
	}

	public void inactivate() {
		this.status = INACTIVE;
	}

	public void inactivateIfExpired() {
		if (isExpired()) {
			inactivate();
		}
	}

	private boolean isExpired() {
		return LocalDateTime.now().isAfter(expiredAt);
	}

	public boolean isActive() {
		return status == ACTIVE;
	}
}
