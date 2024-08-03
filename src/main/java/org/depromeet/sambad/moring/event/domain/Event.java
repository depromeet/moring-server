package org.depromeet.sambad.moring.event.domain;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static org.depromeet.sambad.moring.event.domain.EventStatus.*;

import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;

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

	private Event(Long userId, Long meetingId, EventType type, EventStatus status) {
		this.userId = userId;
		this.meetingId = meetingId;
		this.type = type;
		this.status = status;
	}

	public static Event publish(Long userId, Long meetingId, EventType type) {
		return new Event(userId, meetingId, type, ACTIVE);
	}

	public void inactivate() {
		this.status = INACTIVE;
	}
}
