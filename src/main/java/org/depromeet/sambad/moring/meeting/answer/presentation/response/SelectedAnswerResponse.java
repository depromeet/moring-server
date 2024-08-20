package org.depromeet.sambad.moring.meeting.answer.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberListResponse;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberSummaryResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record SelectedAnswerResponse(
	@Schema(description = "답변 내용", example = "[\"토마토\"]", requiredMode = REQUIRED)
	List<String> content,

	@Schema(description = "해당 모임을 선택한 모임원 수", example = "3", requiredMode = REQUIRED)
	int count,

	@Schema(description = "선택한 모임원 목록", requiredMode = REQUIRED)
	List<MeetingMemberSummaryResponse> selectedMembers
) {
	public static SelectedAnswerResponse from(List<MeetingMember> members, List<MeetingAnswer> meetingAnswers) {
		return new SelectedAnswerResponse(
			meetingAnswers.stream()
				.map(MeetingAnswer::getAnswerContent)
				.distinct()
				.toList(),
			members.size(),
			MeetingMemberListResponse.from(members).contents()
		);
	}
}

