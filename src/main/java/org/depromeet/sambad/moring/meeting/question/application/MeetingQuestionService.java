package org.depromeet.sambad.moring.meeting.question.application;

import java.time.Clock;
import java.time.LocalDateTime;

import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.exception.DuplicateMeetingQuestionException;
import org.depromeet.sambad.moring.meeting.question.presentation.exception.InvalidMeetingMemberTargetException;
import org.depromeet.sambad.moring.meeting.question.presentation.exception.NotFoundMeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.request.MeetingQuestionRequest;
import org.depromeet.sambad.moring.meeting.question.presentation.response.ActiveMeetingQuestionResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.FullInactiveMeetingQuestionListResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MostInactiveMeetingQuestionListResponse;
import org.depromeet.sambad.moring.question.application.QuestionService;
import org.depromeet.sambad.moring.question.domain.Question;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingQuestionService {

	private final MeetingQuestionRepository meetingQuestionRepository;

	private final MeetingMemberService meetingMemberService;
	private final QuestionService questionService;

	private final Clock clock;

	@Transactional
	public void save(Long userId, Long meetingId, MeetingQuestionRequest request) {
		MeetingMember loginMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		MeetingMember targetMember = meetingMemberService.getById(request.meetingMemberId());
		validateTargetMember(loginMember, targetMember);

		Meeting meeting = targetMember.getMeeting();
		Question question = questionService.getById(request.questionId());
		validateNonDuplicateMeetingQuestion(meeting, question);

		MeetingQuestion meetingQuestion = MeetingQuestion.builder()
			.meeting(meeting)
			.targetMember(targetMember)
			.question(question)
			.now(LocalDateTime.now(clock))
			.build();
		meetingQuestionRepository.save(meetingQuestion);
	}

	public ActiveMeetingQuestionResponse findActiveOne(Long userId, Long meetingId) {
		MeetingMember meetingMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		Meeting meeting = meetingMember.getMeeting();
		return meetingQuestionRepository.findActiveOneByMeeting(meeting.getId(), meetingMember.getId());
	}

	public MostInactiveMeetingQuestionListResponse findMostInactiveList(Long userId, Long meetingId) {
		MeetingMember meetingMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		Meeting meeting = meetingMember.getMeeting();
		return meetingQuestionRepository.findMostInactiveList(meeting.getId(), meetingMember.getId());
	}

	public FullInactiveMeetingQuestionListResponse findFullInactiveList(Long userId, Long meetingId,
		PageRequest pageRequest) {
		MeetingMember meetingMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		Meeting meeting = meetingMember.getMeeting();
		return meetingQuestionRepository.findFullInactiveList(meeting.getId(), meetingMember.getId(), pageRequest);
	}

	public MeetingQuestion getById(Long id) {
		return meetingQuestionRepository.findById(id).orElseThrow(NotFoundMeetingQuestion::new);
	}

	private void validateNonDuplicateMeetingQuestion(Meeting meeting, Question question) {
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

