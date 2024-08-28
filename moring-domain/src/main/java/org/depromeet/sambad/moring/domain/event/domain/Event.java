package org.depromeet.sambad.moring.domain.event.domain;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static org.depromeet.sambad.moring.domain.event.domain.EventStatus.*;
import static org.depromeet.sambad.moring.domain.meeting.question.domain.MeetingQuestion.*;

import java.time.LocalDateTime;

import org.depromeet.sambad.moring.domain.common.domain.BaseTimeEntity;

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

	private Event(
		Long userId, Long meetingId, EventType type, EventStatus status, String message, LocalDateTime expiredAt
	) {
		this.userId = userId;
		this.meetingId = meetingId;
		this.type = type;
		this.status = status;
		this.message = message;
		this.expiredAt = expiredAt;
	}

	public static Event publish(Long userId, Long meetingId, EventType type, String message) {
		LocalDateTime expiredAt = LocalDateTime.now().plusSeconds(RESPONSE_TIME_LIMIT_SECONDS);
		return new Event(userId, meetingId, type, ACTIVE, message, expiredAt);
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

	public boolean isHandWavingEvent() {
		return type == EventType.HAND_WAVING_REQUESTED;
	}
}
