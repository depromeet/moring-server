package org.depromeet.sambad.moring.meeting.question.application;

import java.util.Optional;

import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MeetingQuestionPollingResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingQuestionPollingService {

	private final MeetingQuestionRepository meetingQuestionRepository;

	private final MeetingMemberService meetingMemberService;

	public Optional<MeetingQuestionPollingResponse> getNewMeetingQuestion(Long userId, Long meetingId,
		Long currentMeetingQuestionId) {
		Optional<MeetingQuestion> activeMeetingQuestion = meetingQuestionRepository.findActiveOneByMeeting(meetingId);

		if (isNewQuestion(activeMeetingQuestion, currentMeetingQuestionId)) {
			MeetingMember loginMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
			MeetingQuestionPollingResponse meetingQuestionPollingResponse = MeetingQuestionPollingResponse.newQuestionOf(
				activeMeetingQuestion.get(), loginMember);
			return Optional.of(meetingQuestionPollingResponse);
		}
		return Optional.empty();
	}

	private boolean isNewQuestion(Optional<MeetingQuestion> activeMeetingQuestion, Long currentMeetingQuestionId) {
		return activeMeetingQuestion.isPresent() && activeMeetingQuestion.get().getId() != currentMeetingQuestionId;
	}
}