package org.depromeet.sambad.moring.meeting.member.presentation.response;

import java.util.List;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;

public record MeetingMemberSummaryListResponse(
	List<MeetingMemberSummaryResponse> content
) {

	public static MeetingMemberSummaryListResponse of(List<MeetingMember> members) {
		List<MeetingMemberSummaryResponse> memberResponses = members.stream()
			.map(MeetingMemberSummaryResponse::from)
			.toList();
		return new MeetingMemberSummaryListResponse(memberResponses);
	}
}
