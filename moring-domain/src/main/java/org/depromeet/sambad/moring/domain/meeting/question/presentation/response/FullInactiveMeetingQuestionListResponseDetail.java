package org.depromeet.sambad.moring.domain.meeting.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.domain.file.presentation.annotation.FullFileUrl;
import org.depromeet.sambad.moring.domain.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.domain.meeting.member.presentation.response.MeetingMemberSummaryResponse;
import org.depromeet.sambad.moring.domain.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.domain.question.presentation.response.QuestionTitleResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record FullInactiveMeetingQuestionListResponseDetail(
	@Schema(example = "1", description = "모임 질문 식별자", requiredMode = REQUIRED)
	Long meetingQuestionId,

	@FullFileUrl
	@Schema(example = "https://avatars.githubusercontent.com/u/173370739?v=4", description = "모임 질문 이미지 URL",
		requiredMode = REQUIRED)
	String questionImageFileUrl,

	@Schema(description = "질문 제목 정보", requiredMode = REQUIRED)
	QuestionTitleResponse questionTitle,

	@Schema(example = "갖고 싶은 초능력은?", description = "모임 질문 TITLE", requiredMode = REQUIRED)
	String title,

	@Schema(example = "18", description = "모임 내 질문 인덱스로 1 부터 시작합니다.", requiredMode = REQUIRED)
	int questionNumber,

	@Schema(example = "1722707775774", description = "릴레이 질문 시작 시간", requiredMode = REQUIRED)
	Long startTime,

	@Schema(description = "질문 대상자에 대한 정보", requiredMode = REQUIRED)
	MeetingMemberSummaryResponse targetMember
) {

	public static FullInactiveMeetingQuestionListResponseDetail from(MeetingQuestion meetingQuestion) {
		Meeting meeting = meetingQuestion.getMeeting();
		int questionNumber = meeting.getMeetingQuestions().indexOf(meetingQuestion) + 1;

		return FullInactiveMeetingQuestionListResponseDetail.builder()
			.meetingQuestionId(meetingQuestion.getId())
			.questionImageFileUrl(meetingQuestion.getQuestionImageUrl())
			.questionTitle(QuestionTitleResponse.from(meetingQuestion.getQuestion()))
			.title(meetingQuestion.getTitle())
			.questionNumber(questionNumber)
			.startTime(meetingQuestion.getEpochMilliStartTime())
			.targetMember(MeetingMemberSummaryResponse.from(meetingQuestion.getTargetMember()))
			.build();
	}

	public static List<FullInactiveMeetingQuestionListResponseDetail> from(List<MeetingQuestion> meetingQuestions) {
		return meetingQuestions.stream()
			.map(FullInactiveMeetingQuestionListResponseDetail::from)
			.toList();
	}
}
