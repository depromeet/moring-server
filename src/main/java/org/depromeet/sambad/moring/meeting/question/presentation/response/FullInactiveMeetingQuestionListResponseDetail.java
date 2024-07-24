package org.depromeet.sambad.moring.meeting.question.presentation.response;

import java.util.List;

import org.depromeet.sambad.moring.common.DateTimeFormatter;
import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberListResponseDetail;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record FullInactiveMeetingQuestionListResponseDetail(
	@Schema(example = "1", description = "모임 질문 식별자")
	Long meetingQuestionId,
	@Schema(example = "https://avatars.githubusercontent.com/u/173370739?v=4", description = "모임 질문 이미지 URL")
	String questionImageFileUrl,
	@Schema(example = "갖고 싶은 초능력은?", description = "모임 질문 TITLE")
	String title,
	@Schema(example = "18", description = "모임 내 질문 인덱스로 1 부터 시작합니다.")
	int questionNumber,
	@Schema(example = "2024-07-09", description = "릴레이 질문 시작 날짜")
	String startDate,
	@Schema(description = "질문 대상자에 대한 정보")
	MeetingMemberListResponseDetail targetMember
) {

	public static FullInactiveMeetingQuestionListResponseDetail from(MeetingQuestion meetingQuestion) {
		Meeting meeting = meetingQuestion.getMeeting();
		int questionNumber = meeting.getMeetingQuestions().indexOf(meetingQuestion) + 1;

		return FullInactiveMeetingQuestionListResponseDetail.builder()
			.meetingQuestionId(meetingQuestion.getId())
			.questionImageFileUrl(meetingQuestion.getQuestionImageUrl())
			.title(meetingQuestion.getTitle())
			.questionNumber(questionNumber)
			.startDate(DateTimeFormatter.format(meetingQuestion.getStartDate()))
			.targetMember(MeetingMemberListResponseDetail.from(meetingQuestion.getTargetMember()))
			.build();
	}

	public static List<FullInactiveMeetingQuestionListResponseDetail> from(List<MeetingQuestion> meetingQuestions) {
		return meetingQuestions.stream()
			.map(FullInactiveMeetingQuestionListResponseDetail::from)
			.toList();
	}
}
