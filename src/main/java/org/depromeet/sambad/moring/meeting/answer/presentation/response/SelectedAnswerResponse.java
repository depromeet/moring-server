package org.depromeet.sambad.moring.meeting.answer.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberListResponse;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberListResponseDetail;

import io.swagger.v3.oas.annotations.media.Schema;

public record SelectedAnswerResponse(
	@Schema(description = "답변 내용", example = "[\"토마토\"]", requiredMode = REQUIRED)
	List<String> content,

	@Schema(description = "답변 수", example = "3", requiredMode = REQUIRED)
	int count,

	@Schema(description = "선택한 멤버들", requiredMode = REQUIRED)
	List<MeetingMemberListResponseDetail> selectedMembers
) {
	public static SelectedAnswerResponse from(List<MeetingMember> members, List<MeetingAnswer> meetingAnswers) {
		return new SelectedAnswerResponse(
			meetingAnswers.stream()
				.map(MeetingAnswer::getAnswerContent)
				.distinct()
				.toList(),
			meetingAnswers.size(),
			MeetingMemberListResponse.from(members).contents()
		);
	}
}

