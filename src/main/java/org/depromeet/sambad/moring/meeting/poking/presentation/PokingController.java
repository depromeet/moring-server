package org.depromeet.sambad.moring.meeting.poking.presentation;

import org.depromeet.sambad.moring.meeting.poking.application.PokingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "콕 찌르기", description = "콕 찌르기 관련 api / 담당자 : 이한음")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/pokings")
public class PokingController {
	private final PokingService pokingService;
}
