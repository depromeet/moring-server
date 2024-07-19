package org.depromeet.sambad.moring.meeting.comment.presentation.comment.response;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.sambad.moring.meeting.comment.domain.comment.MeetingQuestionComment;
import org.depromeet.sambad.moring.meeting.comment.presentation.reply.response.MeetingQuestionCommentReplyResponse;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberListResponseDetail;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberResponse;

import lombok.Builder;

@Builder
public record MeetingQuestionCommentResponse(
	Long id,
	String content,
	MeetingMemberListResponseDetail writer,

	List<MeetingQuestionCommentReplyResponse> commentReplies,

	int commentReplyCount
) {
	// public static MeetingQuestionCommentResponse from(MeetingQuestionComment meetingQuestionComment) {
	// 	MeetingMember writer = meetingQuestionComment.getMeetingMember();
	// 	MeetingQuestionCommentResponseBuilder builder = MeetingQuestionCommentResponse.builder()
	// 		.id(meetingQuestionComment.getId())
	// 		.content(meetingQuestionComment.getContent())
	// 		.writer(MeetingMemberListResponseDetail.from(writer));
	// 	return builder.build();
	// }

	public static MeetingQuestionCommentResponse from(MeetingQuestionComment meetingQuestionComment) {
		MeetingMember writer = meetingQuestionComment.getMeetingMember();
		List<MeetingQuestionCommentReplyResponse> commentReplies = meetingQuestionComment.getCommentReplies().stream()
			.map(MeetingQuestionCommentReplyResponse::from)
			.collect(Collectors.toList());
		int commentReplyCount = commentReplies.size();

		MeetingQuestionCommentResponseBuilder builder = MeetingQuestionCommentResponse.builder()
			.id(meetingQuestionComment.getId())
			.content(meetingQuestionComment.getContent())
			.writer(MeetingMemberListResponseDetail.from(writer))
			.commentReplies(commentReplies)
			.commentReplyCount(commentReplyCount);

		return builder.build();
	}
}
