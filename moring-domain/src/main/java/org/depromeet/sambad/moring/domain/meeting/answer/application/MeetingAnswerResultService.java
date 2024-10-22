package org.depromeet.sambad.moring.domain.meeting.answer.application;

import java.util.List;

import org.depromeet.sambad.moring.domain.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.domain.meeting.answer.presentation.response.SelectedAnswerResponse;
import org.depromeet.sambad.moring.domain.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMemberValidator;
import org.depromeet.sambad.moring.domain.meeting.question.application.MeetingQuestionRepository;
import org.depromeet.sambad.moring.domain.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.domain.meeting.question.presentation.exception.NotFoundMeetingQuestion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MeetingAnswerResultService {

	private final MeetingQuestionRepository meetingQuestionRepository;
	private final MeetingAnswerRepository meetingAnswerRepository;

	private final MeetingMemberService meetingMemberService;

	private final MeetingMemberValidator meetingMemberValidator;

	public SelectedAnswerResponse getMostSelectedAnswer(Long userId, Long meetingId, Long meetingQuestionId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);

		MeetingQuestion meetingQuestion = meetingQuestionRepository.findByMeetingIdAndMeetingQuestionId(
				meetingId, meetingQuestionId)
			.orElseThrow(NotFoundMeetingQuestion::new);

		List<MeetingAnswer> meetingAnswers = meetingAnswerRepository.findMostSelected(meetingQuestion.getId());

		List<Long> answerIds = MeetingAnswer.getDistinctAnswerIds(meetingAnswers);
		List<MeetingMember> members = meetingAnswerRepository.findMeetingMembersSelectWith(
			meetingQuestion.getId(), answerIds);

		return SelectedAnswerResponse.from(members, meetingAnswers);
	}

	public SelectedAnswerResponse getSelectedSameAnswer(Long userId, Long meetingId, Long meetingQuestionId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		MeetingMember meetingMemberOfRequestedUser = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);

		List<MeetingAnswer> meetingAnswers = meetingAnswerRepository.findByMeetingQuestionIdAndMeetingMemberId(
			meetingQuestionId, meetingMemberOfRequestedUser.getId());

		List<Long> answerIds = MeetingAnswer.getDistinctAnswerIds(meetingAnswers);
		List<MeetingMember> members = meetingAnswerRepository.findMeetingMembersSelectWith(meetingQuestionId, answerIds)
			.stream()
			.filter(meetingMemberOfRequestedUser::isNotEqualMemberWith)
			.toList();

		return SelectedAnswerResponse.from(members, meetingAnswers);
	}
}
