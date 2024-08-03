package org.depromeet.sambad.moring.meeting.answer.application;

import static org.depromeet.sambad.moring.event.domain.EventType.*;

import java.time.Clock;
import java.time.LocalDateTime;

import org.depromeet.sambad.moring.answer.application.AnswerService;
import org.depromeet.sambad.moring.answer.domain.Answer;
import org.depromeet.sambad.moring.event.application.EventService;
import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.meeting.answer.presentation.exception.DuplicateMeetingAnswerException;
import org.depromeet.sambad.moring.meeting.answer.presentation.request.MeetingAnswerRequest;
import org.depromeet.sambad.moring.meeting.answer.presentation.response.MyMeetingAnswerListResponse;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.application.MeetingQuestionRepository;
import org.depromeet.sambad.moring.meeting.question.application.MeetingQuestionService;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingAnswerService {

	private final MeetingAnswerRepository meetingAnswerRepository;
	private final MeetingQuestionRepository meetingQuestionRepository;

	private final MeetingMemberService meetingMemberService;
	private final MeetingQuestionService meetingQuestionService;
	private final AnswerService answerService;
	private final EventService eventService;

	private final Clock clock;

	@Transactional
	public void save(Long userId, Long meetingId, Long meetingQuestionId, MeetingAnswerRequest request) {
		MeetingMember loginMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		MeetingQuestion meetingQuestion = meetingQuestionService.getById(meetingId, meetingQuestionId);
		meetingQuestion.validateNotFinished(LocalDateTime.now(clock));
		validateNonDuplicateMeetingAnswer(meetingQuestion.getId(), loginMember.getId());

		Answer selectedAnswer = answerService.getById(meetingQuestion.getQuestion().getId(), request.answerId());
		MeetingAnswer meetingAnswer = MeetingAnswer
			.builder()
			.meetingMember(loginMember)
			.meetingQuestion(meetingQuestion)
			.answer(selectedAnswer)
			.build();
		meetingAnswerRepository.save(meetingAnswer);

		eventService.inactivateLastEventByType(userId, meetingId, QUESTION_REGISTERED);
		advanceToNextQuestionIfAllAnswered(meetingId, meetingQuestion);
	}

	private void advanceToNextQuestionIfAllAnswered(Long meetingId, MeetingQuestion currentQuestion) {
		boolean isAllAnswered = meetingAnswerRepository.isAllAnsweredByMeetingIdAndMeetingQuestionId(
			meetingId, currentQuestion.getId());
		if (isAllAnswered) {
			currentQuestion.updateStatusToInactive();
			meetingQuestionRepository.findNextQuestion(meetingId)
				.ifPresent(nextQuestion -> {
					nextQuestion.updateStatusToActive(LocalDateTime.now(clock));
					MeetingMember targetMember = nextQuestion.getTargetMember();

					eventService.publish(targetMember.getUserId(), meetingId, TARGET_MEMBER);
				});
		}
	}

	public MyMeetingAnswerListResponse getMyList(Long userId, Long meetingId) {
		MeetingMember loginMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		return meetingAnswerRepository.findAllByMeetingMemberId(loginMember.getId());
	}

	private void validateNonDuplicateMeetingAnswer(Long meetingQuestionId, Long meetingMemberId) {
		boolean exists = meetingAnswerRepository.existsByMeetingMember(meetingQuestionId, meetingMemberId);
		if (exists) {
			throw new DuplicateMeetingAnswerException();
		}
	}
}