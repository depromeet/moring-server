package org.depromeet.sambad.moring.domain.meeting.handwaving.domain;

import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;

public record HandWavedMemberDto(
	MeetingMember handWavedMember,
	HandWaving handWaving
) {
	public Long getMemberId() {
		return handWavedMember.getId();
	}

	public HandWavingStatus getStatus() {
		return handWaving.getStatus();
	}
}
