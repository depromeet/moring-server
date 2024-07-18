package org.depromeet.sambad.moring.question.application;

import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.question.domain.Question;
import org.depromeet.sambad.moring.question.presentation.exception.NotFoundQuestionException;
import org.depromeet.sambad.moring.question.presentation.response.QuestionListResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

	private final QuestionRepository questionRepository;

	private final MeetingMemberService meetingMemberService;

	public Question getById(Long id) {
		return questionRepository.findById(id)
			.orElseThrow(NotFoundQuestionException::new);
	}

	public QuestionListResponse findQuestions(Long userId, int page, int size) {
		MeetingMember loginMember = meetingMemberService.getByUserId(userId);
		Meeting meeting = loginMember.getMeeting();
		return questionRepository.findQuestionsByMeeting(meeting.getId(), PageRequest.of(page, size));
	}
}
