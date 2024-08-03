package org.depromeet.sambad.moring.meeting.question.application;

import static org.depromeet.sambad.moring.event.domain.EventType.*;

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
import org.depromeet.sambad.moring.meeting.question.presentation.response.ActiveMeetingQuestionResponse;
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
	public ActiveMeetingQuestionResponse save(Long userId, Long meetingId, MeetingQuestionRequest request) {
		MeetingMember loginMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		MeetingMember nextTargetMember = meetingMemberService.getById(request.meetingMemberId());
		loginMember.validateNextTarget(nextTargetMember);

		Optional<MeetingQuestion> activeMeetingQuestion = findActiveMeetingQuestion(meetingId);
		Question activeQuestion = questionService.getById(request.questionId());
		validateNonDuplicateQuestion(meetingId, activeQuestion.getId());

		Meeting meeting = loginMember.getMeeting();
		MeetingQuestion nowMeetingQuestion = null;
		if (activeMeetingQuestion.isPresent()) {
			nowMeetingQuestion = activeMeetingQuestion.get();
			nowMeetingQuestion.setQuestion(loginMember, activeQuestion);
		} else {
			nowMeetingQuestion = createActiveQuestion(meeting, loginMember, activeQuestion);
		}

		eventService.inactivateLastEventByType(userId, meetingId, TARGET_MEMBER);
		meeting.getMeetingMembers().forEach(member ->
			eventService.publish(member.getUser().getId(), meetingId, QUESTION_REGISTERED));

		MeetingQuestion nextMeetingQuestion = MeetingQuestion.createNextMeetingQuestion(
			meeting, nextTargetMember, nowMeetingQuestion.getNextStartTime());
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
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		return meetingQuestionRepository.findMostInactiveList(meetingId);
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

	private Optional<MeetingQuestion> findActiveMeetingQuestion(Long meetingId) {
		return meetingQuestionRepository.findActiveOneByMeeting(meetingId);
	}

	private void validateNonDuplicateQuestion(Long meetingId, Long questionId) {
		boolean isDuplicateQuestion = meetingQuestionRepository.existsByQuestion(meetingId, questionId);
		if (isDuplicateQuestion) {
			throw new DuplicateQuestionException();
		}
	}
}

