package org.depromeet.sambad.moring.meeting.comment.presentation.comment.response;

import java.util.List;

import org.depromeet.sambad.moring.meeting.comment.domain.comment.MeetingQuestionComment;

public record MeetingCommentListResponse(
	List<MeetingCommentListResponseDetail> content
) {
	public static MeetingCommentListResponse from(List<MeetingQuestionComment> comments) {
		return new MeetingCommentListResponse(MeetingCommentListResponseDetail.from(comments));
	}
}
