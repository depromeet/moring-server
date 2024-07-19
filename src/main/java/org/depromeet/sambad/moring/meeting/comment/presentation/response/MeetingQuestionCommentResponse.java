package org.depromeet.sambad.moring.meeting.comment.presentation.response;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

import lombok.Builder;

@Builder
public record MeetingQuestionCommentResponse(
		Long id,
		String content,
		MeetingMember writer,
		MeetingQuestion meetingQuestion
) {

	public static MeetingQuestionCommentResponse of(
		Long id,
		String content,
		MeetingMember writer,
		MeetingQuestion meetingQuestion
	) {
		return MeetingQuestionCommentResponse.builder()
			.id(id)
			.content(content)
			.writer(writer)
			.meetingQuestion(meetingQuestion)
			.build();
	}
}
