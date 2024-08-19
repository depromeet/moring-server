package org.depromeet.sambad.moring.meeting.question.application;

import static org.depromeet.sambad.moring.event.domain.EventType.*;
import static org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestionStatus.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.event.application.EventService;
import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberValidator;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberListResponse;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.exception.NotFoundMeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.request.MeetingQuestionRequest;
import org.depromeet.sambad.moring.meeting.question.presentation.response.CurrentMeetingQuestionResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.FullInactiveMeetingQuestionListResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MeetingQuestionAndAnswerListResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MeetingQuestionStatisticsDetail;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MeetingQuestionStatisticsResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MostInactiveMeetingQuestionListResponse;
import org.depromeet.sambad.moring.question.application.QuestionService;
import org.depromeet.sambad.moring.question.domain.Question;
import org.depromeet.sambad.moring.question.presentation.exception.DuplicateQuestionException;
import org.depromeet.sambad.moring.question.presentation.response.QuestionResponse;
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
	private final EventService eventService;

	private final MeetingMemberValidator meetingMemberValidator;

	private final Clock clock;

	@Transactional
	public CurrentMeetingQuestionResponse save(Long userId, Long meetingId, MeetingQuestionRequest request) {
		MeetingMember loginMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		MeetingMember nextTargetMember = meetingMemberService.getById(request.meetingMemberId());
		loginMember.validateNextTarget(nextTargetMember);

		Optional<MeetingQuestion> registeredMeetingQuestion = meetingQuestionRepository.findFirstByMeetingIdAndStatus(
			meetingId, ACTIVE);
		Question activeQuestion = questionService.getById(request.questionId());
		validateNonDuplicateQuestion(meetingId, activeQuestion.getId());

		Meeting meeting = loginMember.getMeeting();
		MeetingQuestion currentMeetingQuestion;

		if (registeredMeetingQuestion.isPresent()) {
			currentMeetingQuestion = registeredMeetingQuestion.get();
			currentMeetingQuestion.registerQuestion(loginMember, activeQuestion);
		} else {
			currentMeetingQuestion = createActiveQuestion(meeting, loginMember, activeQuestion);
		}

		eventService.inactivateLastEventByType(userId, meetingId, TARGET_MEMBER);
		meeting.getMeetingMembers().forEach(member ->
			eventService.publish(member.getUser().getId(), meetingId, QUESTION_REGISTERED));

		MeetingQuestion nextMeetingQuestion = MeetingQuestion.createNextMeetingQuestion(
			meeting, nextTargetMember, currentMeetingQuestion.getNextStartTime(), meeting.getTotalMemberCount());
		meetingQuestionRepository.save(nextMeetingQuestion);

		return CurrentMeetingQuestionResponse.questionRegisteredOf(currentMeetingQuestion, nextTargetMember, false);
	}

	@Transactional
	public MeetingQuestion createActiveQuestion(Meeting meeting, MeetingMember targetMember, Question activeQuestion) {
		MeetingQuestion activeMeetingQuestion = MeetingQuestion.createActiveMeetingQuestion(meeting, targetMember,
			activeQuestion, LocalDateTime.now(clock), meeting.getTotalMemberCount());
		return meetingQuestionRepository.save(activeMeetingQuestion);
	}

	public MeetingQuestionAndAnswerListResponse getActiveMeetingQuestionAndAnswerList(Long userId, Long meetingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);

		MeetingQuestion activeMeetingQuestion = meetingQuestionRepository.findCurrentActiveOne(meetingId)
			.orElseThrow(NotFoundMeetingQuestion::new);
		return MeetingQuestionAndAnswerListResponse.of(activeMeetingQuestion);
	}

	public Optional<CurrentMeetingQuestionResponse> findCurrentOne(Long userId, Long meetingId) {
		MeetingMember meetingMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		Meeting meeting = meetingMember.getMeeting();
		Optional<MeetingQuestion> activeMeetingQuestionOpt = meetingQuestionRepository.findCurrentOne(
			meeting.getId());

		if (activeMeetingQuestionOpt.isPresent()) {
			MeetingQuestion activeMeetingQuestion = activeMeetingQuestionOpt.get();
			return Optional.of(getCurrentMeetingQuestionResponse(activeMeetingQuestion, meetingMember));
		}
		return Optional.empty();
	}

	public MostInactiveMeetingQuestionListResponse findTopInactiveList(Long userId, Long meetingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		return meetingQuestionRepository.findTopInactiveList(meetingId);
	}

	public FullInactiveMeetingQuestionListResponse findFullInactiveList(Long userId, Long meetingId,
		PageRequest pageRequest) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		return meetingQuestionRepository.findFullInactiveList(meetingId, pageRequest);
	}

	public MeetingQuestion getById(Long meetingId, Long meetingQuestionId) {
		return meetingQuestionRepository.findByMeetingIdAndMeetingQuestionId(meetingId, meetingQuestionId)
			.orElseThrow(NotFoundMeetingQuestion::new);
	}

	/*
	 * 질문 정보의 경우 Public한 데이터이므로, 따로 권한 체크를 하지 않는다.
	 */
	public QuestionResponse getQuestionResponseById(Long meetingId, Long meetingQuestionId) {
		MeetingQuestion meetingQuestion = getById(meetingId, meetingQuestionId);
		return QuestionResponse.from(meetingQuestion.getQuestion());
	}

	public MeetingQuestionStatisticsResponse getStatistics(Long userId, Long meetingId, Long meetingQuestionId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		MeetingQuestion meetingQuestion = getById(meetingId, meetingQuestionId);
		List<MeetingQuestionStatisticsDetail> statistics = meetingQuestionRepository.findStatistics(
			meetingQuestion.getId());

		return MeetingQuestionStatisticsResponse.of(statistics);
	}

	public MeetingMemberListResponse getMeetingMembersByMeetingQuestionId(
		Long userId, Long meetingId, Long meetingQuestionId
	) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		MeetingQuestion meetingQuestion = getById(meetingId, meetingQuestionId);

		List<MeetingMember> members = meetingQuestionRepository.findMeetingMembersByMeetingQuestionId(
			meetingQuestion.getId());

		return MeetingMemberListResponse.from(members);
	}

	private CurrentMeetingQuestionResponse getCurrentMeetingQuestionResponse(
		MeetingQuestion activeMeetingQuestion, MeetingMember meetingMember) {
		if (activeMeetingQuestion.getQuestion() == null) {
			return CurrentMeetingQuestionResponse.questionNotRegisteredOf(activeMeetingQuestion);
		} else {
			MeetingMember nextTargetMember = meetingQuestionRepository.findFirstByStatusAndStartTimeAfterOrderByStartTime(
					NOT_STARTED,
					LocalDateTime.now())
				.map(MeetingQuestion::getTargetMember)
				.orElse(null);

			return CurrentMeetingQuestionResponse.questionRegisteredOf(activeMeetingQuestion,
				nextTargetMember,
				meetingQuestionRepository.isAnswered(activeMeetingQuestion.getId(), meetingMember.getId()));
		}
	}

	private void validateNonDuplicateQuestion(Long meetingId, Long questionId) {
		boolean isDuplicateQuestion = meetingQuestionRepository.existsByQuestion(meetingId, questionId);
		if (isDuplicateQuestion) {
			throw new DuplicateQuestionException();
		}
	}
}

