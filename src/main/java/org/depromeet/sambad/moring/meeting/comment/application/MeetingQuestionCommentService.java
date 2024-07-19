package org.depromeet.sambad.moring.meeting.comment.application;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.sambad.moring.meeting.comment.domain.MeetingQuestionComment;
import org.depromeet.sambad.moring.meeting.comment.presentation.exception.InvalidCommentWriterException;
import org.depromeet.sambad.moring.meeting.comment.presentation.exception.NotFoundMeetingQuestionCommentException;
import org.depromeet.sambad.moring.meeting.comment.presentation.request.MeetingQuestionCommentRequest;
import org.depromeet.sambad.moring.meeting.comment.presentation.response.MeetingQuestionCommentResponse;
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

	@Transactional
	public void delete(Long userId, Long meetingQuestionCommentId) {
		MeetingMember meetingMember = meetingMemberService.getByUserId(userId);
		MeetingQuestionComment meetingQuestionComment = getById(meetingQuestionCommentId);
		isSameWriter(meetingMember, meetingQuestionComment.getMeetingMember());
		meetingQuestionCommentRepository.delete(meetingQuestionComment);
	}

	public List<MeetingQuestionCommentResponse> getAllComments(Long meetingQuestionId) {
		MeetingQuestion meetingQuestion = meetingQuestionService.getById(meetingQuestionId);
		List<MeetingQuestionComment> meetingQuestionComments = getAllCommentsByMeetingQuestion(meetingQuestion);
		return meetingQuestionComments.stream()
			.map(MeetingQuestionComment -> MeetingQuestionCommentResponse.of(
				MeetingQuestionComment.getId(),
				MeetingQuestionComment.getContent(),
				MeetingQuestionComment.getMeetingMember(),
				MeetingQuestionComment.getMeetingQuestion()
			)).collect(Collectors.toList());
	}

	private void isSameWriter(MeetingMember meetingMember, MeetingMember writer) {
		if (!meetingMember.equals(writer)) {
			throw new InvalidCommentWriterException();
		}
	}

	public MeetingQuestionComment getById(Long id) {
		return meetingQuestionCommentRepository.findById(id)
			.orElseThrow(NotFoundMeetingQuestionCommentException::new);
	}

	public List<MeetingQuestionComment> getAllCommentsByMeetingQuestion(MeetingQuestion meetingQuestion) {
		return meetingQuestionCommentRepository.findAllByMeetingQuestion(meetingQuestion);
	}
}
