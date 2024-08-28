package org.depromeet.sambad.moring.domain.meeting.comment.application.reply;

import org.depromeet.sambad.moring.domain.meeting.comment.application.comment.MeetingQuestionCommentService;
import org.depromeet.sambad.moring.domain.meeting.comment.domain.comment.MeetingQuestionComment;
import org.depromeet.sambad.moring.domain.meeting.comment.domain.reply.MeetingQuestionCommentReply;
import org.depromeet.sambad.moring.domain.meeting.comment.presentation.reply.exception.NotFoundMeetingQuestionCommentReplyException;
import org.depromeet.sambad.moring.domain.meeting.comment.presentation.reply.request.MeetingQuestionCommentReplyRequest;
import org.depromeet.sambad.moring.domain.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.domain.meeting.question.application.MeetingQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingQuestionCommentReplyService {
	private final MeetingQuestionCommentReplyRepository meetingQuestionCommentReplyRepository;

	private final MeetingMemberService meetingMemberService;
	private final MeetingQuestionService meetingQuestionService;
	private final MeetingQuestionCommentService meetingQuestionCommentService;

	@Transactional
	public void save(Long userId, Long meetingId, Long meetingQuestionId, Long parentCommentId,
		MeetingQuestionCommentReplyRequest request) {
		MeetingMember loginMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		MeetingQuestionComment parentComment = meetingQuestionCommentService.getById(meetingQuestionId,
			parentCommentId);

		MeetingQuestionCommentReply childComment = MeetingQuestionCommentReply.builder()
			.meetingMember(loginMember)
			.meetingQuestionComment(parentComment)
			.content(request.content())
			.build();

		meetingQuestionCommentReplyRepository.save(childComment);
		parentComment.addCommentReply(childComment);
	}

	@Transactional
	public void delete(Long userId, Long meetingId, Long meetingQuestionId, Long meetingQuestionCommentReplyId) {
		MeetingMember loginMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		// TODO: getById() 대신 검증 메서드 만들어서 호출하는 방식으로 수정
		meetingQuestionService.getById(meetingId, meetingQuestionId);

		MeetingQuestionCommentReply commentReply = getById(meetingQuestionCommentReplyId);
		commentReply.validateWriter(loginMember);

		MeetingQuestionComment parentComment = commentReply.getMeetingQuestionComment();
		parentComment.removeCommentReply(commentReply);

		meetingQuestionCommentReplyRepository.delete(commentReply);
	}

	public MeetingQuestionCommentReply getById(Long childCommentId) {
		return meetingQuestionCommentReplyRepository.findById(childCommentId)
			.orElseThrow(NotFoundMeetingQuestionCommentReplyException::new);
	}
}
