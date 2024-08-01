package org.depromeet.sambad.moring.meeting.meeting.presentation.response;

import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;

public record MeetingNameResponse(
	String title
) {
	public static MeetingNameResponse from(Meeting meeting) {
		return new MeetingNameResponse(meeting.getName());
	}
}
