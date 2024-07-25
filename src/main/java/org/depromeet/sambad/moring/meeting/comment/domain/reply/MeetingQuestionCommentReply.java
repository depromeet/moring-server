package org.depromeet.sambad.moring.meeting.comment.domain.reply;

import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.meeting.comment.domain.comment.MeetingQuestionComment;
import org.depromeet.sambad.moring.meeting.comment.presentation.comment.exception.InvalidCommentWriterException;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingQuestionCommentReply extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meeting_question_comment_reply_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meeting_question_comment_id")
	private MeetingQuestionComment meetingQuestionComment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meeting_member_id")
	private MeetingMember meetingMember;

	private String content;

	@Builder
	public MeetingQuestionCommentReply(MeetingQuestionComment meetingQuestionComment, MeetingMember meetingMember,
		String content) {
		this.meetingQuestionComment = meetingQuestionComment;
		this.meetingMember = meetingMember;
		this.content = content;
	}

	public void validateWriter(MeetingMember meetingMember) {
		if (!meetingMember.equals(this.meetingMember)) {
			throw new InvalidCommentWriterException();
		}
	}
}
