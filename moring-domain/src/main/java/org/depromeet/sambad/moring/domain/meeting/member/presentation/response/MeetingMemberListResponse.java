package org.depromeet.sambad.moring.domain.meeting.member.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.ArrayList;
import java.util.List;

import org.depromeet.sambad.moring.domain.meeting.handwaving.domain.HandWavedMemberDto;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeetingMemberListResponse(
	@Schema(
		description = "모임원 목록",
		requiredMode = REQUIRED
	)
	List<MeetingMemberListResponseDetail> contents
) {

	public static MeetingMemberListResponse from(
		MeetingMember me, List<MeetingMember> notHandWavedMembers, List<HandWavedMemberDto> handWavedMembers
	) {
		List<MeetingMember> sortedHandWavedMembers = handWavedMembers.stream()
			.map(HandWavedMemberDto::handWavedMember)
			.sorted()
			.toList();

		List<MeetingMember> sortedNotHandWavedMembers = notHandWavedMembers.stream()
			.sorted()
			.toList();

		List<MeetingMember> mergedMembers = mergeMeetingMembers(me, sortedHandWavedMembers, sortedNotHandWavedMembers);

		List<MeetingMemberListResponseDetail> memberResponses = mergedMembers.stream()
			.map(member -> MeetingMemberListResponseDetail.from(member, handWavedMembers))
			.toList();

		return new MeetingMemberListResponse(memberResponses);
	}

	private static List<MeetingMember> mergeMeetingMembers(
		MeetingMember me, List<MeetingMember> sortedHandWavedMember, List<MeetingMember> sortedNotHandWavedMembers
	) {
		List<MeetingMember> mergedMembers = new ArrayList<>();

		// 다음과 같은 순서대로 모임원 목록을 구성
		mergedMembers.add(me);
		mergedMembers.addAll(sortedHandWavedMember);
		mergedMembers.addAll(sortedNotHandWavedMembers);

		return mergedMembers;
	}
}
