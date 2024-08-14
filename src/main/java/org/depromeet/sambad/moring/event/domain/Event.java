package org.depromeet.sambad.moring.event.domain;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static org.depromeet.sambad.moring.event.domain.EventStatus.ACTIVE;
import static org.depromeet.sambad.moring.event.domain.EventStatus.INACTIVE;
import static org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion.*;

import java.time.LocalDateTime;

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

	private LocalDateTime expiredAt;

	private Event(Long userId, Long meetingId, EventType type, EventStatus status, LocalDateTime expiredAt) {
		this.userId = userId;
		this.meetingId = meetingId;
		this.type = type;
		this.status = status;
		this.expiredAt = expiredAt;
	}

	public static Event publish(Long userId, Long meetingId, EventType type) {
		LocalDateTime expiredAt = LocalDateTime.now().plusHours(RESPONSE_TIME_LIMIT_HOURS);
		return new Event(userId, meetingId, type, ACTIVE, expiredAt);
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
