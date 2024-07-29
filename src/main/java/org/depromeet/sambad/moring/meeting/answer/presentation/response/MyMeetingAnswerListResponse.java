package org.depromeet.sambad.moring.meeting.answer.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import org.depromeet.sambad.moring.meeting.answer.infrastructure.dto.MyMeetingAnswerResponseCustom;

import io.swagger.v3.oas.annotations.media.Schema;

public record MyMeetingAnswerListResponse(
	@Schema(
		description = "내가 작성한 답변 목록",
		example = "[{\"idx\":1,\"title\":\"갖고 싶은 초능력은?\",\"content\":\"분신술\",\"commentContent\":\"요새 할 일이 너무 많아요ㅠ 분신술로 시간 단축!!\"}]",
		requiredMode = REQUIRED
	)
	List<MyMeetingAnswerListResponseDetail> contents
) {

	public static MyMeetingAnswerListResponse from(List<MyMeetingAnswerResponseCustom> content) {
		return new MyMeetingAnswerListResponse(MyMeetingAnswerListResponseDetail.from(content));
	}
}
