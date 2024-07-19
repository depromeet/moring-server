package org.depromeet.sambad.moring.meeting.comment.domain.comment;

import java.util.ArrayList;
import java.util.List;

import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.meeting.comment.domain.reply.MeetingQuestionCommentReply;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingQuestionComment extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meeting_question_id")
	private MeetingQuestion meetingQuestion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meeting_member_id")
	private MeetingMember meetingMember;

	@OneToMany(mappedBy = "meetingQuestionComment", fetch = FetchType.LAZY)
	private List<MeetingQuestionCommentReply> commentReplies = new ArrayList<>();

	private String content;

	@Builder
	public MeetingQuestionComment(MeetingQuestion meetingQuestion, MeetingMember meetingMember, String content) {
		this.meetingQuestion = meetingQuestion;
		this.meetingMember = meetingMember;
		this.content = content;
	}

	public void addCommentReply(MeetingQuestionCommentReply commentReply) {
		commentReplies.add(commentReply);
	}

	public void removeCommentReply(MeetingQuestionCommentReply commentReply) {
		commentReplies.remove(commentReply);
	}
}
