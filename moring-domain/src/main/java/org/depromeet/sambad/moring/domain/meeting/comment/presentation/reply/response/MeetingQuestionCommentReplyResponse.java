package org.depromeet.sambad.moring.domain.meeting.comment.presentation.reply.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import org.depromeet.sambad.moring.domain.meeting.comment.domain.reply.MeetingQuestionCommentReply;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.domain.meeting.member.presentation.response.MeetingMemberSummaryResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MeetingQuestionCommentReplyResponse(
	@Schema(description = "릴레이질문 코멘트 답글 ID", example = "1", requiredMode = REQUIRED)
	Long meetingQuestionCommentReplyId,

	@Schema(description = "릴레이질문 코멘트 답글 내용", example = "코멘트 답글 예시 입니다.", requiredMode = REQUIRED)
	String content,

	@Schema(description = "릴레이질문 코멘트 답글 작성자", example = "{\"meetingMemberId\":1,\"name\":\"이한음\",\"profileImageFileUrl\":\"https://example.com\",\"role\":\"OWNER\"}", requiredMode = REQUIRED)
	MeetingMemberSummaryResponse writer
) {
	public static MeetingQuestionCommentReplyResponse from(MeetingQuestionCommentReply commentReply) {
		MeetingMember writer = commentReply.getMeetingMember();
		MeetingQuestionCommentReplyResponseBuilder builder = MeetingQuestionCommentReplyResponse.builder()
			.meetingQuestionCommentReplyId(commentReply.getId())
			.content(commentReply.getContent())
			.writer(MeetingMemberSummaryResponse.from(writer));
		return builder.build();
	}
}
