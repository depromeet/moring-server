package org.depromeet.sambad.moring.meeting.comment.presentation.comment.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import org.depromeet.sambad.moring.meeting.comment.domain.comment.MeetingQuestionComment;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberListResponseDetail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MeetingCommentListResponseDetail(

	@Schema(description = "릴레이질문 코멘트 ID", example = "1", requiredMode = REQUIRED)
	Long id,
	@Schema(description = "릴레이질문 코멘트 내용", example = "코멘트 예시 입니다.", requiredMode = REQUIRED)
	String content,
	@Schema(description = "코멘트 작성자", requiredMode = REQUIRED)
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
