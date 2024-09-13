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
		// 1순위: 나

		// 2순위: 손을 흔든 사용자 중 수락한 사용자
		List<MeetingMember> sortedHandWavingAcceptedMembers = handWavedMembers.stream()
			.distinct()
			.filter(HandWavedMemberDto::isAccepted)
			.map(HandWavedMemberDto::handWavedMember)
			.sorted()
			.toList();

		// 3순위: 손을 흔든 사용자 중 아직 요청 상태인 사용자
		List<MeetingMember> sortedHandWavingRequestedMembers = handWavedMembers.stream()
			.distinct()
			.filter(HandWavedMemberDto::isRequested)
			.map(HandWavedMemberDto::handWavedMember)
			.sorted()
			.toList();

		// 4순위: 손을 흔들지 않은 사용자
		List<MeetingMember> sortedNotHandWavedMembers = notHandWavedMembers.stream()
			.distinct()
			.sorted()
			.toList();

		List<MeetingMember> mergedMembers = mergeMeetingMembers(
			List.of(me),
			sortedHandWavingRequestedMembers,
			sortedHandWavingAcceptedMembers,
			sortedNotHandWavedMembers
		);

		List<MeetingMemberListResponseDetail> memberResponses = mergedMembers.stream()
			.map(member -> MeetingMemberListResponseDetail.from(member, handWavedMembers))
			.toList();

		return new MeetingMemberListResponse(memberResponses);
	}

	private static List<MeetingMember> mergeMeetingMembers(List<MeetingMember>... memberLists) {
		List<MeetingMember> mergedMembers = new ArrayList<>();

		for (List<MeetingMember> members : memberLists) {
			mergedMembers.addAll(members);
		}

		return mergedMembers;
	}
}
