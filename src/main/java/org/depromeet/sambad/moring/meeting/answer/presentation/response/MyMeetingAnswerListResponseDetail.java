package org.depromeet.sambad.moring.meeting.answer.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;
import java.util.stream.IntStream;

import org.depromeet.sambad.moring.meeting.answer.infrastructure.dto.MyMeetingAnswerResponseCustom;

import io.swagger.v3.oas.annotations.media.Schema;

public record MyMeetingAnswerListResponseDetail(
	@Schema(title = "질문 인덱스", example = "1", requiredMode = REQUIRED)
	int idx,

	@Schema(title = "릴레이 질문 제목", example = "갖고 싶은 초능력은?", requiredMode = REQUIRED)
	String title,

	@Schema(title = "유저가 선택한 답변", example = "분신술", requiredMode = REQUIRED)
	String content,

	@Schema(title = "유저가 단 댓글", example = "요새 할 일이 너무 많아요ㅠ 분신술로 시간 단축!!",
		description = "댓글이 없으면 null 응답합니다.", requiredMode = NOT_REQUIRED)
	String commentContent
) {

	public static List<MyMeetingAnswerListResponseDetail> from(List<MyMeetingAnswerResponseCustom> responseCustoms) {
		return IntStream.range(0, responseCustoms.size())
			.mapToObj(i -> {
				MyMeetingAnswerResponseCustom response = responseCustoms.get(i);
				return new MyMeetingAnswerListResponseDetail(
					i + 1,
					response.meetingQuestion().getTitle(),
					response.meetingAnswer().getAnswer().getContent(),
					response.comment() != null ? response.comment().getContent() : null
				);
			})
			.toList();
	}
}