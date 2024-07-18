package org.depromeet.sambad.moring.meeting.question.application;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.exception.DuplicateMeetingQuestionException;
import org.depromeet.sambad.moring.meeting.question.presentation.exception.InvalidMeetingMemberTargetException;
import org.depromeet.sambad.moring.meeting.question.presentation.request.MeetingQuestionRequest;
import org.depromeet.sambad.moring.question.application.QuestionService;
import org.depromeet.sambad.moring.question.domain.Question;
import org.depromeet.sambad.moring.user.application.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingQuestionService {

	private final MeetingQuestionRepository meetingQuestionRepository;

	private final UserService userService;
	private final MeetingMemberService meetingMemberService;
	private final QuestionService questionService;

	private final Clock clock;

	@Transactional
	public void save(Long userId, MeetingQuestionRequest request) {
		// FIXME: 모임, 모임원 붙이기, 예외 docs 추가
		MeetingMember loginMember = meetingMemberService.getByUserId(userId);
		MeetingMember targetMember = meetingMemberService.getById(request.meetingMemberId());
		validateTargetMember(loginMember, targetMember);

		Meeting meeting = meetingMember.getMeeting();
		Question question = questionService.getById(request.questionId());
		validateDuplicateMeetingQuestion(meeting, question);

		MeetingQuestion meetingQuestion = MeetingQuestion.builder()
			.meeting(meeting)
			.targetMember(targetMember)
			.question(question)
			.now(LocalDateTime.now(clock))
			.build();
		meetingQuestionRepository.save(meetingQuestion);
	}

	private void validateDuplicateMeetingQuestion(Meeting meeting, Question question) {
		boolean isDuplicateQuestion = meetingQuestionRepository.existsByQuestion(meeting.getId(), question.getId());
		if (isDuplicateQuestion) {
			throw new DuplicateMeetingQuestionException();
		}
	}

	private void validateTargetMember(MeetingMember loginMember, MeetingMember targetMember) {
		if (loginMember.isOtherMeeting(targetMember) || loginMember.equals(targetMember)) {
			throw new InvalidMeetingMemberTargetException();
		}
	}
}

