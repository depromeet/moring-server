package org.depromeet.sambad.moring.meeting.comment.application.comment;

import java.util.List;

import org.depromeet.sambad.moring.meeting.comment.domain.comment.MeetingQuestionComment;
import org.depromeet.sambad.moring.meeting.comment.presentation.comment.exception.NotFoundMeetingQuestionCommentException;
import org.depromeet.sambad.moring.meeting.comment.presentation.comment.request.MeetingQuestionCommentRequest;
import org.depromeet.sambad.moring.meeting.comment.presentation.comment.response.MeetingCommentListResponse;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberValidator;
import org.depromeet.sambad.moring.meeting.question.application.MeetingQuestionService;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingQuestionCommentService {

	private final MeetingQuestionCommentRepository meetingQuestionCommentRepository;

	private final MeetingMemberValidator meetingMemberValidator;

	private final MeetingMemberService meetingMemberService;
	private final MeetingQuestionService meetingQuestionService;

	@Transactional
	public void save(Long userId, Long meetingId, MeetingQuestionCommentRequest request) {
		MeetingMember meetingMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		MeetingQuestion meetingQuestion = meetingQuestionService.getById(meetingId, request.meetingQuestionId());

		MeetingQuestionComment meetingQuestionComment = MeetingQuestionComment.builder()
			.meetingMember(meetingMember)
			.meetingQuestion(meetingQuestion)
			.content(request.content())
			.build();

		meetingQuestionCommentRepository.save(meetingQuestionComment);
	}

	@Transactional
	public void delete(Long userId, Long meetingId, Long meetingQuestionId, Long meetingQuestionCommentId) {
		MeetingMember meetingMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		MeetingQuestionComment meetingQuestionComment = getById(meetingQuestionId,
			meetingQuestionCommentId);
		meetingQuestionComment.validateWriter(meetingMember);
		meetingQuestionCommentRepository.delete(meetingQuestionComment);
	}

	public MeetingCommentListResponse getAllComments(Long userId, Long meetingId, Long meetingQuestionId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		List<MeetingQuestionComment> comments = meetingQuestionCommentRepository.findAllByMeetingQuestionId(
			meetingQuestionId);
		return MeetingCommentListResponse.from(comments);
	}

	public MeetingQuestionComment getById(Long meetingQuestionId, Long meetingCommentId) {
		return meetingQuestionCommentRepository.findByIdAndMeetingQuestionId(meetingCommentId, meetingQuestionId)
			.orElseThrow(NotFoundMeetingQuestionCommentException::new);
	}
}