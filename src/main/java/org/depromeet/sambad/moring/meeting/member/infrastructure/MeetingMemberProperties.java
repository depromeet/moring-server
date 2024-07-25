package org.depromeet.sambad.moring.meeting.member.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "meeting.member")
public record MeetingMemberProperties(
	int meetingMaxCount,
	int meetingMemberMaxCount,
	int hostMaxCount
) {
}
