package org.depromeet.sambad.moring.meeting.handWaving.presentation;

import org.depromeet.sambad.moring.meeting.handWaving.application.HandWavingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "손 흔들어 인사하기", description = "손 흔들어 인사하기 관련 api / 담당자 : 이한음")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class HandWavingController {
	private final HandWavingService handWavingService;
}
