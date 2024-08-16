package org.depromeet.sambad.moring.meeting.handWaving.application;

import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HandWavingService {
	private final HandWavingRepository handWavingRepository;
	private final MeetingMemberService meetingMemberService;
}
