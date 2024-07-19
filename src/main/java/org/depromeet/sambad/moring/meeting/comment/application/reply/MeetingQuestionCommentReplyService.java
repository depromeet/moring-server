package org.depromeet.sambad.moring.meeting.comment.application.reply;

import org.depromeet.sambad.moring.meeting.comment.application.comment.MeetingQuestionCommentRepository;
import org.depromeet.sambad.moring.meeting.comment.application.comment.MeetingQuestionCommentService;
import org.depromeet.sambad.moring.meeting.comment.domain.comment.MeetingQuestionComment;
import org.depromeet.sambad.moring.meeting.comment.domain.reply.MeetingQuestionCommentReply;
import org.depromeet.sambad.moring.meeting.comment.presentation.reply.exception.InvalidCommentReplyWriterException;
import org.depromeet.sambad.moring.meeting.comment.presentation.reply.exception.NotFoundMeetingQuestionCommentReplyException;
import org.depromeet.sambad.moring.meeting.comment.presentation.reply.request.MeetingQuestionCommentReplyRequest;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingQuestionCommentReplyService {
	private final MeetingQuestionCommentReplyRepository meetingQuestionCommentReplyRepository;
	private final MeetingQuestionCommentRepository meetingQuestionCommentRepository;

	private final MeetingMemberService meetingMemberService;
	private final MeetingQuestionCommentService meetingQuestionCommentService;

	@Transactional
	public void save(Long userId, MeetingQuestionCommentReplyRequest request) {
		MeetingMember meetingMember = meetingMemberService.getByUserId(userId);
		MeetingQuestionComment comment = meetingQuestionCommentService.getById(request.meetingQuestionCommentId());
		meetingQuestionCommentService.validateMeeting(meetingMember, comment.getMeetingQuestion());

		MeetingQuestionCommentReply commentReply = MeetingQuestionCommentReply.builder()
			.meetingMember(meetingMember)
			.meetingQuestionComment(comment)
			.content(request.content())
			.build();

		comment.addCommentReply(commentReply);
		meetingQuestionCommentRepository.save(comment);

		meetingQuestionCommentReplyRepository.save(commentReply);
	}

	@Transactional
	public void delete(Long userId, Long meetingQuestionCommentReplyId) {
		MeetingMember meetingMember = meetingMemberService.getByUserId(userId);
		MeetingQuestionCommentReply commentReply = getById(meetingQuestionCommentReplyId);
		isSameWriter(meetingMember, commentReply.getMeetingMember());

		MeetingQuestionComment comment = commentReply.getMeetingQuestionComment();
		comment.removeCommentReply(commentReply);
		meetingQuestionCommentRepository.save(comment);

		meetingQuestionCommentReplyRepository.delete(commentReply);
	}

	private void isSameWriter(MeetingMember meetingMember, MeetingMember writer) {
		if (!meetingMember.equals(writer)) {
			throw new InvalidCommentReplyWriterException();
		}
	}

	public MeetingQuestionCommentReply getById(Long id) {
		return meetingQuestionCommentReplyRepository.findById(id)
			.orElseThrow(NotFoundMeetingQuestionCommentReplyException::new);
	}
}
