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
import org.depromeet.sambad.moring.meeting.question.presentation.response.MeetingQuestionAndAnswerListResponse;
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
	public ActiveMeetingQuestionResponse save(Long userId, Long meetingId, MeetingQuestionRequest request) {
		MeetingMember loginMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		MeetingMember nextTargetMember = meetingMemberService.getById(request.meetingMemberId());
		loginMember.validateNextTarget(nextTargetMember);

		Optional<MeetingQuestion> activeMeetingQuestion = findActiveMeetingQuestion(meetingId);
		Question activeQuestion = questionService.getById(request.questionId());
		validateNonDuplicateMeetingQuestion(meetingId, activeQuestion.getId());

		Meeting meeting = loginMember.getMeeting();
		MeetingQuestion nowMeetingQuestion = null;
		if (activeMeetingQuestion.isPresent()) {
			activeMeetingQuestion.get().setQuestion(loginMember, activeQuestion);
			nowMeetingQuestion = activeMeetingQuestion.get();
		} else {
			nowMeetingQuestion = createActiveQuestion(meeting, loginMember, activeQuestion);
		}

		MeetingQuestion nextMeetingQuestion = MeetingQuestion.createNextMeetingQuestion(meeting, nextTargetMember,
			activeMeetingQuestion, LocalDateTime.now(clock));
		meetingQuestionRepository.save(nextMeetingQuestion);

		return ActiveMeetingQuestionResponse.questionRegisteredOf(nowMeetingQuestion, false);
	}

	@Transactional
	public MeetingQuestion createActiveQuestion(Meeting meeting, MeetingMember targetMember, Question activeQuestion) {
		MeetingQuestion activeMeetingQuestion = MeetingQuestion.createActiveMeetingQuestion(meeting, targetMember,
			activeQuestion, LocalDateTime.now(clock));
		return meetingQuestionRepository.save(activeMeetingQuestion);
	}

	public MeetingQuestionAndAnswerListResponse getActiveMeetingQuestionAndAnswerList(Long userId, Long meetingId) {
		MeetingMember loginMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		Optional<MeetingQuestion> activeMeetingQuestion = findActiveMeetingQuestion(loginMember.getMeeting().getId());
		if (activeMeetingQuestion.isEmpty()) {
			throw new NotFoundMeetingQuestion();
		}
		if (activeMeetingQuestion.get().getQuestion() == null) {
			throw new NotFoundMeetingQuestion();
		}
		return MeetingQuestionAndAnswerListResponse.of(activeMeetingQuestion.get());
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

	private Optional<MeetingQuestion> findActiveMeetingQuestion(Long meetingId) {
		return meetingQuestionRepository.findActiveOneByMeeting(meetingId);
	}

	private void validateNonDuplicateMeetingQuestion(Long meetingId, Long questionId) {
		boolean isDuplicateQuestion = meetingQuestionRepository.existsByQuestion(meetingId, questionId);
		if (isDuplicateQuestion) {
			throw new DuplicateMeetingQuestionException();
		}
	}
}

