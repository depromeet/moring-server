package org.depromeet.sambad.moring.meeting.comment.presentation.comment.response;

import java.util.List;

import org.depromeet.sambad.moring.meeting.comment.domain.comment.MeetingQuestionComment;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberListResponseDetail;

import lombok.Builder;

@Builder
public record MeetingCommentListResponseDetail(
	Long id,
	String content,
	MeetingMemberListResponseDetail writer
) {
	public static MeetingCommentListResponseDetail from(MeetingQuestionComment meetingQuestionComment) {
		MeetingMember writer = meetingQuestionComment.getMeetingMember();

		return MeetingCommentListResponseDetail.builder()
			.id(meetingQuestionComment.getId())
			.content(meetingQuestionComment.getContent())
			.writer(MeetingMemberListResponseDetail.from(writer))
			.build();
	}

	public static List<MeetingCommentListResponseDetail> from(List<MeetingQuestionComment> comments) {
		return comments.stream()
			.map(MeetingCommentListResponseDetail::from)
			.toList();
	}
}
