package org.depromeet.sambad.moring.meeting.answer.application;

import java.util.List;

import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.meeting.answer.presentation.response.SelectedAnswerResponse;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberValidator;
import org.depromeet.sambad.moring.meeting.question.application.MeetingQuestionRepository;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.exception.NotFoundMeetingQuestion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MeetingAnswerResultService {

	private final MeetingMemberService meetingMemberService;
	private final MeetingQuestionRepository meetingQuestionRepository;
	private final MeetingAnswerRepository meetingAnswerRepository;
	private final MeetingMemberValidator meetingMemberValidator;

	public SelectedAnswerResponse getMostSelectedAnswer(Long userId, Long meetingId, Long meetingQuestionId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);

		MeetingQuestion meetingQuestion = meetingQuestionRepository.findByMeetingIdAndMeetingQuestionId(
				meetingId, meetingQuestionId)
			.orElseThrow(NotFoundMeetingQuestion::new);

		List<MeetingAnswer> answers = meetingAnswerRepository.findMostSelected(meetingQuestion.getId());

		List<MeetingMember> members = meetingAnswerRepository.findMeetingMembersSelectWith(
			meetingQuestionId, MeetingAnswer.getAnswerIds(answers));

		return SelectedAnswerResponse.from(members, answers);
	}

	public SelectedAnswerResponse getSelectedSameAnswer(Long userId, Long meetingId, Long meetingQuestionId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		MeetingMember meetingMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);

		List<MeetingAnswer> answers = meetingAnswerRepository.findByMeetingQuestionIdAndMeetingMemberId(
			meetingQuestionId, meetingMember.getId());

		List<MeetingMember> members = meetingAnswerRepository.findMeetingMembersSelectWith(
			meetingQuestionId, MeetingAnswer.getAnswerIds(answers));

		return SelectedAnswerResponse.from(members, answers);
	}
}
