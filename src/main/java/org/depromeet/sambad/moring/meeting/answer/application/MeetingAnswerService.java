package org.depromeet.sambad.moring.meeting.answer.application;

import static org.depromeet.sambad.moring.event.domain.EventType.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import org.depromeet.sambad.moring.answer.application.AnswerService;
import org.depromeet.sambad.moring.event.application.EventService;
import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.meeting.answer.presentation.exception.DuplicateMeetingAnswerException;
import org.depromeet.sambad.moring.meeting.answer.presentation.request.MeetingAnswerRequest;
import org.depromeet.sambad.moring.meeting.answer.presentation.response.MeetingAnswerListResponse;
import org.depromeet.sambad.moring.meeting.answer.presentation.response.MyMeetingAnswerListResponse;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberValidator;
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

	private final MeetingMemberValidator meetingMemberValidator;

	private final Clock clock;

	@Transactional
	public void save(Long userId, Long meetingId, Long meetingQuestionId, MeetingAnswerRequest request) {
		MeetingMember loginMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		MeetingQuestion meetingQuestion = meetingQuestionService.getById(meetingId, meetingQuestionId);

		meetingQuestion.validateNotFinished(LocalDateTime.now(clock));
		validateNonDuplicateMeetingAnswer(meetingQuestion.getId(), loginMember.getId());
		meetingQuestion.validateMeetingAnswerCount(request.answerIds().size());

		saveMeetingAnswers(meetingQuestion, loginMember, request.answerIds());

		eventService.inactivateLastEventByType(userId, meetingId, QUESTION_REGISTERED);
		advanceToNextQuestionIfAllAnswered(meetingId, meetingQuestion);
	}

	public MyMeetingAnswerListResponse getListByMe(Long userId, Long meetingId) {
		MeetingMember loginMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		return meetingAnswerRepository.findAllByMyMeetingMemberId(loginMember.getId());
	}

	public MeetingAnswerListResponse getListByMember(Long userId, Long meetingId, Long targetMemberId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		meetingMemberValidator.validateMemberIsMemberOfMeeting(targetMemberId, meetingId);
		return meetingAnswerRepository.findAllByMeetingMemberId(targetMemberId);
	}

	private void saveMeetingAnswers(MeetingQuestion meetingQuestion, MeetingMember loginMember, List<Long> answerIds) {
		Long questionId = meetingQuestion.getQuestion().getId();
		List<MeetingAnswer> meetingAnswers = answerIds.stream()
			.map(answerId -> answerService.getById(questionId, answerId))
			.map(answer -> new MeetingAnswer(meetingQuestion, answer, loginMember))
			.toList();
		meetingAnswers.forEach(meetingAnswerRepository::save);
	}

	private void validateNonDuplicateMeetingAnswer(Long meetingQuestionId, Long meetingMemberId) {
		boolean exists = meetingAnswerRepository.existsByMeetingMember(meetingQuestionId, meetingMemberId);
		if (exists) {
			throw new DuplicateMeetingAnswerException();
		}
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

					eventService.publish(targetMember.getUser().getId(), meetingId, TARGET_MEMBER);
				});
		}
	}

	@Transactional
	public void updateHidden(Long userId, Long meetingId, List<Long> activeMeetingQuestionIds) {
		MeetingMember member = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);

		List<MeetingAnswer> hiddenAnswers = meetingAnswerRepository.findAllByMeetingMemberIdAndMeetingQuestionIdNotIn(
			member.getId(), activeMeetingQuestionIds);
		List<MeetingAnswer> activeAnswers = meetingAnswerRepository.findAllByMeetingMemberIdAndMeetingQuestionIdIn(
			member.getId(), activeMeetingQuestionIds);

		hiddenAnswers.forEach(MeetingAnswer::updateStatusHidden);
		activeAnswers.forEach(MeetingAnswer::updateStatusActive);
	}
}