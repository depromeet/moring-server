package org.depromeet.sambad.moring.meeting.member.presentation;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;

public record MeetingMemberResponse(
	Long meetingMemberId,
	String name,
	String profileImageUrl
) {

	public static MeetingMemberResponse from(final MeetingMember meetingMember) {
		return new MeetingMemberResponse(
			meetingMember.getId(),
			meetingMember.getUser().getName(),
			meetingMember.getUser().getProfileImageUrl()
		);
	}
}
