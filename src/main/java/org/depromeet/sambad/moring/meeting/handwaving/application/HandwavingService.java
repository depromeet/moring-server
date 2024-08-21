package org.depromeet.sambad.moring.meeting.handwaving.application;

import static org.depromeet.sambad.moring.event.domain.EventType.HAND_WAVING_REQUESTED;
import static org.depromeet.sambad.moring.event.domain.EventType.HAND_WAVING_RESENT;

import org.depromeet.sambad.moring.event.application.EventService;
import org.depromeet.sambad.moring.meeting.handwaving.domain.Handwaving;
import org.depromeet.sambad.moring.meeting.handwaving.presentation.request.HandwavingRequest;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HandwavingService {
	private final HandwavingRepository handWavingRepository;
	private final MeetingMemberService meetingMemberService;
	private final MeetingMemberValidator meetingMemberValidator;
	private final EventService eventService;

	@Transactional
	public void sendHandWaving(Long userId, Long meetingId, HandwavingRequest request) {
		MeetingMember sender = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);

		MeetingMember receiver = meetingMemberService.getById(request.receiverMemberId());
		meetingMemberValidator.validateUserIsMemberOfMeeting(receiver.getUser().getId(), meetingId);

		Handwaving handWaving = Handwaving.send(sender, receiver);
		handWavingRepository.save(handWaving);
		eventService.publish(userId, meetingId, HAND_WAVING_REQUESTED);
	}

	@Transactional
	public void resendHandWaving(Long userId, Long meetingId, Long handWavingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		Handwaving handWaving = handWavingRepository.getById(handWavingId);
		handWaving.resend();
		eventService.publish(userId, meetingId, HAND_WAVING_RESENT);
	}

	@Transactional
	public void ignoreHandWaving(Long userId, Long meetingId, Long handWavingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		Handwaving handWaving = handWavingRepository.getById(handWavingId);
		handWaving.reject();
	}
}
