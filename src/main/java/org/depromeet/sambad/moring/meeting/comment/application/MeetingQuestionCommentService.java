package org.depromeet.sambad.moring.meeting.comment.application;

import org.depromeet.sambad.moring.meeting.comment.domain.MeetingQuestionComment;
import org.depromeet.sambad.moring.meeting.comment.presentation.request.MeetingQuestionCommentRequest;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
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

	private final MeetingMemberService meetingMemberService;
	private final MeetingQuestionService meetingQuestionService;

	@Transactional
	public void save(Long userId, MeetingQuestionCommentRequest request) {
		MeetingMember meetingMember = meetingMemberService.getByUserId(userId);
		MeetingQuestion meetingQuestion = meetingQuestionService.getById(request.meetingQuestionId());

		MeetingQuestionComment meetingQuestionComment = MeetingQuestionComment.builder()
			.meetingMember(meetingMember)
			.meetingQuestion(meetingQuestion)
			.content(request.content())
			.build();

		meetingQuestionCommentRepository.save(meetingQuestionComment);
	}
}
