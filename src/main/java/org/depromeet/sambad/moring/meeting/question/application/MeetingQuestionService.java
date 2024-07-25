package org.depromeet.sambad.moring.meeting.question.application;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.exception.DuplicateMeetingQuestionException;
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
		MeetingMember nextTargetMember = meetingMemberService.getById(request.meetingMemberId());

		Optional<MeetingQuestion> activeQuestion = findActiveQuestion(meetingId);

		//TODO: 현재 진행 중인 질문이 없는 경우: 예외 처리

		activeQuestion.get().isTarget(loginMember);
		loginMember.validateTargetMember(nextTargetMember);

		Question nextQuestion = questionService.getById(request.questionId());
		validateNonDuplicateMeetingQuestion(meetingId, nextQuestion.getId());

		MeetingQuestion nextMeetingQuestion = MeetingQuestion.createNotFirstQuestion(loginMember.getMeeting(),
			nextTargetMember, activeQuestion, LocalDateTime.now(clock));
		meetingQuestionRepository.save(nextMeetingQuestion);
	}

	@Transactional
	public void createFirstQuestion(Meeting meeting) {
		MeetingQuestion firstQuestion = MeetingQuestion.createFirstQuestion(meeting, LocalDateTime.now(clock));
		meetingQuestionRepository.save(firstQuestion);
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

	public MeetingQuestion getById(Long meetingId, Long meetingQuestionId) {
		return meetingQuestionRepository.findByMeetingIdAndMeetingQuestionId(meetingId, meetingQuestionId)
			.orElseThrow(NotFoundMeetingQuestion::new);
	}

	private Optional<MeetingQuestion> findActiveQuestion(Long meetingId) {
		return meetingQuestionRepository.findActiveOneByMeeting(meetingId);
	}

	private void validateNonDuplicateMeetingQuestion(Long meetingId, Long questionId) {
		boolean isDuplicateQuestion = meetingQuestionRepository.existsByQuestion(meetingId, questionId);
		if (isDuplicateQuestion) {
			throw new DuplicateMeetingQuestionException();
		}
	}
}

