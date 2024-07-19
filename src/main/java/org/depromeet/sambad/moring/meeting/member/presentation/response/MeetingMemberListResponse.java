package org.depromeet.sambad.moring.meeting.member.presentation.response;

import java.util.List;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberResponse.MeetingMemberResponseDetail;

public record MeetingMemberListResponse(
	List<MeetingMemberResponseDetail> content
) {

	public static MeetingMemberListResponse of(List<MeetingMember> members) {
		List<MeetingMemberResponseDetail> memberResponses = members.stream()
			.map(MeetingMemberResponseDetail::from)
			.toList();
		return new MeetingMemberListResponse(memberResponses);
	}
}
