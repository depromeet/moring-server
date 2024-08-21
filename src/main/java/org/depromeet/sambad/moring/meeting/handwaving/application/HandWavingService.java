package org.depromeet.sambad.moring.meeting.handwaving.application;

import static org.depromeet.sambad.moring.event.domain.EventType.HAND_WAVING_REQUESTED;

import org.depromeet.sambad.moring.event.application.EventService;
import org.depromeet.sambad.moring.meeting.handwaving.domain.HandWaving;
import org.depromeet.sambad.moring.meeting.handwaving.presentation.exception.NotFoundHandWavingException;
import org.depromeet.sambad.moring.meeting.handwaving.presentation.request.HandWavingRequest;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HandWavingService {
	private final HandWavingRepository handWavingRepository;
	private final MeetingMemberService meetingMemberService;
	private final MeetingMemberValidator meetingMemberValidator;
	private final EventService eventService;

	@Transactional
	public void sendHandWaving(Long userId, Long meetingId, HandWavingRequest request) {
		MeetingMember sender = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);

		meetingMemberValidator.validateMemberIsMemberOfMeeting(request.receiverMemberId(), meetingId);
		MeetingMember receiver = meetingMemberService.getById(request.receiverMemberId());

		HandWaving handWaving = HandWaving.send(sender, receiver);
		handWavingRepository.save(handWaving);
		eventService.publish(userId, meetingId, HAND_WAVING_REQUESTED);
	}

	@Transactional
	public void acceptHandWaving(Long userId, Long meetingId, Long handWavingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		HandWaving handWaving = getHandWavingById(handWavingId);
		handWaving.validateIsReceiver(userId);
		handWaving.accept();
	}

	@Transactional
	public void ignoreHandWaving(Long userId, Long meetingId, Long handWavingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		HandWaving handWaving = getHandWavingById(handWavingId);
		handWaving.validateIsReceiver(userId);
		handWaving.reject();
	}

	private HandWaving getHandWavingById(Long handWavingId) {
		return handWavingRepository.findById(handWavingId)
			.orElseThrow(NotFoundHandWavingException::new);
	}
}
