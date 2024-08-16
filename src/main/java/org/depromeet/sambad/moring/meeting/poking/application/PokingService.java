package org.depromeet.sambad.moring.meeting.poking.application;

import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PokingService {
	private final PokingRepository pokingRepository;
	private final MeetingMemberService meetingMemberService;
}
