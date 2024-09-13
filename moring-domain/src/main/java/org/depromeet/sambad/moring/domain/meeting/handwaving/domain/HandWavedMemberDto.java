package org.depromeet.sambad.moring.domain.meeting.handwaving.domain;

import java.util.Objects;

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

	public boolean isRequested() {
		return Objects.equals(handWaving.getStatus(), HandWavingStatus.REQUESTED);
	}

	public boolean isAccepted() {
		return Objects.equals(handWaving.getStatus(), HandWavingStatus.ACCEPTED);
	}
}
