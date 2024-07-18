package org.depromeet.sambad.moring.meeting.question.presentation.response;

import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.member.presentation.MeetingMemberResponse;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

import lombok.Builder;

@Builder
public record ActiveMeetingQuestionResponse(
	Long meetingQuestionId,
	String questionImageUrl,
	String title,
	int questionNumber,
	int totalMeetingMemberCount,
	int responseCount,
	Boolean isAnswered,
	MeetingMemberResponse targetMember
) {

	public static ActiveMeetingQuestionResponse of(MeetingQuestion activeMeetingQuestion, Boolean isAnswered) {
		Meeting meeting = activeMeetingQuestion.getMeeting();
		int questionNumber = meeting.getMeetingQuestions().indexOf(activeMeetingQuestion) + 1;
		int totalMeetingMemberCount = meeting.getMeetingMembers().size();
		int responseCount = activeMeetingQuestion.getMemberAnswers().size();

		return ActiveMeetingQuestionResponse.builder()
			.meetingQuestionId(activeMeetingQuestion.getId())
			.questionImageUrl(activeMeetingQuestion.getQuestion().getQuestionImageUrl())
			.title(activeMeetingQuestion.getQuestion().getTitle())
			.questionNumber(questionNumber)
			.totalMeetingMemberCount(totalMeetingMemberCount)
			.responseCount(responseCount)
			.isAnswered(isAnswered)
			.targetMember(MeetingMemberResponse.from(activeMeetingQuestion.getTargetMember()))
			.build();
	}
}
