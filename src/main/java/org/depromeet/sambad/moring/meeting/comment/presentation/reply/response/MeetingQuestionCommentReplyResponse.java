package org.depromeet.sambad.moring.meeting.comment.presentation.reply.response;

import org.depromeet.sambad.moring.meeting.comment.domain.reply.MeetingQuestionCommentReply;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberListResponseDetail;

import lombok.Builder;

@Builder
public record MeetingQuestionCommentReplyResponse(
	Long id,
	String content,
	MeetingMemberListResponseDetail writer
) {
	public static MeetingQuestionCommentReplyResponse from(MeetingQuestionCommentReply commentReply) {
		MeetingMember writer = commentReply.getMeetingMember();
		MeetingQuestionCommentReplyResponseBuilder builder = MeetingQuestionCommentReplyResponse.builder()
			.id(commentReply.getId())
			.content(commentReply.getContent())
			.writer(MeetingMemberListResponseDetail.from(writer));
		return builder.build();
	}
}
