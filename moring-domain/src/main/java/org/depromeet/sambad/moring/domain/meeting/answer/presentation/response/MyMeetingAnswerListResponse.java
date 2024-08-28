package org.depromeet.sambad.moring.domain.meeting.answer.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.depromeet.sambad.moring.domain.meeting.answer.infrastructure.dto.MyMeetingAnswerResponseCustom;

import io.swagger.v3.oas.annotations.media.Schema;

public record MyMeetingAnswerListResponse(
	@Schema(description = "작성한 나의 답변 목록", requiredMode = REQUIRED)
	List<MyMeetingAnswerListResponseDetail> contents
) {

	public static MyMeetingAnswerListResponse from(List<MyMeetingAnswerResponseCustom> content) {
		return new MyMeetingAnswerListResponse(MyMeetingAnswerListResponseDetail.from(content));
	}
}
