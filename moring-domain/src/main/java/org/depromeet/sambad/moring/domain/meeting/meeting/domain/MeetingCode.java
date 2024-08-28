package org.depromeet.sambad.moring.domain.meeting.meeting.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@Embeddable
public class MeetingCode {

	private final String code;

	public MeetingCode(String code) {
		this.code = code;
	}

	public static MeetingCode from(String code) {
		return new MeetingCode(code);
	}
}
