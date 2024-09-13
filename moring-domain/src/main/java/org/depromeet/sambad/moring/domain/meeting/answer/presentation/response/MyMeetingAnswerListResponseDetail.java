package org.depromeet.sambad.moring.domain.meeting.answer.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;
import java.util.stream.IntStream;

import org.depromeet.sambad.moring.domain.meeting.answer.infrastructure.dto.MyMeetingAnswerResponseCustom;
import org.depromeet.sambad.moring.domain.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.domain.question.presentation.response.QuestionTitleResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record MyMeetingAnswerListResponseDetail(
	@Schema(title = "모임 내 질문 식별자", example = "1", requiredMode = REQUIRED)
	Long meetingQuestionId,

	@Schema(title = "질문 인덱스", example = "1", requiredMode = REQUIRED)
	int idx,

	@Schema(description = "질문 제목 정보", requiredMode = REQUIRED)
	QuestionTitleResponse questionTitle,

	@Schema(title = "릴레이 질문 제목", example = "갖고 싶은 초능력은?", requiredMode = REQUIRED)
	String title,

	@Schema(description = "유저가 선택한 답변 리스트", example = "[\"토마토\"]", requiredMode = REQUIRED)
	List<String> content,

	@Schema(title = "유저가 단 댓글", example = "요새 할 일이 너무 많아요ㅠ 분신술로 시간 단축!!",
		description = "댓글이 없으면 null 응답합니다.", requiredMode = NOT_REQUIRED)
	String commentContent,

	@Schema(title = "답변 숨김 여부", example = "false", requiredMode = REQUIRED)
	Boolean isHidden
) {

	public static List<MyMeetingAnswerListResponseDetail> from(List<MyMeetingAnswerResponseCustom> responseCustoms) {
		return IntStream.range(0, responseCustoms.size())
			.mapToObj(i -> {
				MyMeetingAnswerResponseCustom response = responseCustoms.get(i);
				MeetingQuestion meetingQuestion = response.meetingQuestion();
				return new MyMeetingAnswerListResponseDetail(
					meetingQuestion.getId(),
					i + 1,
					QuestionTitleResponse.from(meetingQuestion.getQuestion()),
					meetingQuestion.getFullTitle(),
					response.getMeetingAnswers(),
					response.comment(),
					response.isHidden()
				);
			})
			.toList();
	}
}