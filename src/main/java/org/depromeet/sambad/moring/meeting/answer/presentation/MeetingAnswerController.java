package org.depromeet.sambad.moring.meeting.answer.presentation;

import org.depromeet.sambad.moring.meeting.answer.application.MeetingAnswerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "MeetingAnswer", description = "모임 내 답변 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MeetingAnswerController {

	private final MeetingAnswerService meetingAnswerService;
}
