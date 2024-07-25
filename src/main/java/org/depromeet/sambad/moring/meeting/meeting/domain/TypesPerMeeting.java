package org.depromeet.sambad.moring.meeting.meeting.domain;

import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TypesPerMeeting extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "types_per_meeting_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meeting_id")
	private Meeting meeting;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meeting_type_id")
	private MeetingType meetingType;

	private TypesPerMeeting(Meeting meeting, MeetingType meetingType) {
		this.meeting = meeting;
		this.meetingType = meetingType;
	}

	public static TypesPerMeeting of(Meeting meeting, MeetingType type) {
		return new TypesPerMeeting(meeting, type);
	}
}
