package org.depromeet.sambad.moring.meeting.question.presentation.response;

import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberResponse.MeetingMemberResponseDetail;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

import lombok.Builder;

@Builder
public record MeetingQuestionResponse(
	Long meetingQuestionId,
	String questionImageFileUrl,
	String title,
	int questionNumber,
	int totalMeetingMemberCount,
	int responseCount,
	Boolean isAnswered,
	MeetingMemberResponseDetail targetMember
) {

	public static MeetingQuestionResponse of(MeetingQuestion meetingQuestion, Boolean isAnswered) {
		Meeting meeting = meetingQuestion.getMeeting();
		int questionNumber = meeting.getMeetingQuestions().indexOf(meetingQuestion) + 1;
		int totalMeetingMemberCount = meeting.getMeetingMembers().size();
		int responseCount = meetingQuestion.getMemberAnswers().size();

		return MeetingQuestionResponse.builder()
			.meetingQuestionId(meetingQuestion.getId())
			.questionImageFileUrl(meetingQuestion.getQuestion().getQuestionImageUrl())
			.title(meetingQuestion.getQuestion().getTitle())
			.questionNumber(questionNumber)
			.totalMeetingMemberCount(totalMeetingMemberCount)
			.responseCount(responseCount)
			.isAnswered(isAnswered)
			.targetMember(MeetingMemberResponseDetail.from(meetingQuestion.getTargetMember()))
			.build();
	}
}
