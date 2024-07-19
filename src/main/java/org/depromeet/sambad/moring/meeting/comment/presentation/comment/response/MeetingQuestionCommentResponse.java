package org.depromeet.sambad.moring.meeting.comment.presentation.comment.response;

import org.depromeet.sambad.moring.meeting.comment.domain.comment.MeetingQuestionComment;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberListResponseDetail;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberResponse;

import lombok.Builder;

@Builder
public record MeetingQuestionCommentResponse(
	Long id,
	String content,
	MeetingMemberListResponseDetail writer
) {
	public static MeetingQuestionCommentResponse from(MeetingQuestionComment meetingQuestionComment) {
		MeetingMember writer = meetingQuestionComment.getMeetingMember();
		MeetingQuestionCommentResponseBuilder builder = MeetingQuestionCommentResponse.builder()
			.id(meetingQuestionComment.getId())
			.content(meetingQuestionComment.getContent())
			.writer(MeetingMemberListResponseDetail.from(writer));
		return builder.build();
	}
}
