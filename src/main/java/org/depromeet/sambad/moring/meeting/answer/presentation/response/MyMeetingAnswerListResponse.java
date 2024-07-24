package org.depromeet.sambad.moring.meeting.answer.presentation.response;

import java.util.List;

import org.depromeet.sambad.moring.meeting.answer.infrastructure.dto.MyMeetingAnswerResponseCustom;

public record MyMeetingAnswerListResponse(
	List<MyMeetingAnswerListResponseDetail> content
) {

	public static MyMeetingAnswerListResponse from(List<MyMeetingAnswerResponseCustom> content) {
		return new MyMeetingAnswerListResponse(MyMeetingAnswerListResponseDetail.from(content));
	}
}
