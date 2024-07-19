package org.depromeet.sambad.moring.meeting.comment.presentation.response;

import org.depromeet.sambad.moring.meeting.comment.domain.MeetingQuestionComment;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;

import lombok.Builder;

@Builder
public record MeetingQuestionCommentResponse(
	Long id,
	String content,
	String writerName,
	String profileImagePath
) {
	public static MeetingQuestionCommentResponse from(MeetingQuestionComment meetingQuestionComment) {
		MeetingMember writer = meetingQuestionComment.getMeetingMember();
		MeetingQuestionCommentResponseBuilder builder = MeetingQuestionCommentResponse.builder()
			.id(meetingQuestionComment.getId())
			.content(meetingQuestionComment.getContent())
			.writerName(writer.getName())
			.profileImagePath(writer.getProfileImageUrl());

		return builder.build();
	}
}
