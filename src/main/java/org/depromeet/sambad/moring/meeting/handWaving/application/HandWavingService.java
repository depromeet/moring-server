package org.depromeet.sambad.moring.meeting.handWaving.application;

import static org.depromeet.sambad.moring.event.domain.EventType.HAND_WAVING_REQUESTED;
import static org.depromeet.sambad.moring.event.domain.EventType.HAND_WAVING_RESENT;

import org.depromeet.sambad.moring.event.application.EventService;
import org.depromeet.sambad.moring.meeting.handWaving.domain.HandWaving;
import org.depromeet.sambad.moring.meeting.handWaving.presentation.request.HandWavingRequest;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberValidator;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HandWavingService {
	private final HandWavingRepository handWavingRepository;
	private final MeetingMemberService meetingMemberService;
	private final MeetingMemberValidator meetingMemberValidator;
	private final EventService eventService;

	public void sendHandWaving(Long userId, Long meetingId, HandWavingRequest request) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		MeetingMember sender = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);

		MeetingMember receiver = meetingMemberService.getById(request.receiverId());
		meetingMemberValidator.validateUserIsMemberOfMeeting(receiver.getUser().getId(), meetingId);

		HandWaving handWaving = HandWaving.send(sender, receiver);
		handWavingRepository.save(handWaving);
		eventService.publish(userId, meetingId, HAND_WAVING_REQUESTED);
	}

	public void resendHandWaving(Long userId, Long meetingId, Long handWavingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		HandWaving handWaving = handWavingRepository.findById(handWavingId);
		handWaving.resend();
		handWavingRepository.save(handWaving);
		eventService.publish(userId, meetingId, HAND_WAVING_RESENT);
	}

	public void ignoreHandWaving(Long userId, Long meetingId, Long handWavingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		HandWaving handWaving = handWavingRepository.findById(handWavingId);
		handWaving.reject();
		handWavingRepository.save(handWaving);
	}
}
