package org.depromeet.sambad.moring.domain.meeting.comment.presentation.comment.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.domain.meeting.comment.domain.comment.MeetingQuestionComment;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.domain.meeting.member.presentation.response.MeetingMemberSummaryResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MeetingCommentListResponseDetail(
	@Schema(description = "릴레이질문 코멘트 ID", example = "1", requiredMode = REQUIRED)
	Long meetingQuestionCommentId,

	@Schema(description = "릴레이질문 코멘트 내용", example = "코멘트 예시 입니다.", requiredMode = REQUIRED)
	String content,

	@Schema(description = "코멘트 작성자", requiredMode = REQUIRED)
	MeetingMemberSummaryResponse writer
) {
	public static MeetingCommentListResponseDetail from(MeetingQuestionComment meetingQuestionComment) {
		MeetingMember writer = meetingQuestionComment.getMeetingMember();

		return MeetingCommentListResponseDetail.builder()
			.meetingQuestionCommentId(meetingQuestionComment.getId())
			.content(meetingQuestionComment.getContent())
			.writer(MeetingMemberSummaryResponse.from(writer))
			.build();
	}

	public static List<MeetingCommentListResponseDetail> from(List<MeetingQuestionComment> comments) {
		return comments.stream()
			.map(MeetingCommentListResponseDetail::from)
			.toList();
	}
}
