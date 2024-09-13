package org.depromeet.sambad.moring.domain.meeting.handwaving.application;

import static org.depromeet.sambad.moring.domain.event.domain.EventType.*;
import static org.depromeet.sambad.moring.domain.meeting.handwaving.domain.HandWavingStatus.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.depromeet.sambad.moring.domain.event.application.EventService;
import org.depromeet.sambad.moring.domain.event.domain.Event;
import org.depromeet.sambad.moring.domain.event.domain.EventType;
import org.depromeet.sambad.moring.domain.meeting.handwaving.domain.HandWaving;
import org.depromeet.sambad.moring.domain.meeting.handwaving.domain.HandWavingSummary;
import org.depromeet.sambad.moring.domain.meeting.handwaving.presentation.exception.NotFoundHandWavingException;
import org.depromeet.sambad.moring.domain.meeting.handwaving.presentation.request.HandWavingRequest;
import org.depromeet.sambad.moring.domain.meeting.handwaving.presentation.response.HandWavingStatusResponse;
import org.depromeet.sambad.moring.domain.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMemberValidator;
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

		publishRequestedEvent(handWaving);
	}

	public HandWavingStatusResponse getHandWavingStatus(Long userId, Long meetingId, Long receiverMemberId) {
		MeetingMember sender = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		Optional<HandWaving> handWaving = getHandWavingBySenderIdAndReceiverId(sender.getId(), receiverMemberId)
			.or(() -> getHandWavingBySenderIdAndReceiverId(receiverMemberId, sender.getId()));
		return handWaving.map(waving -> HandWavingStatusResponse.of(waving.getId(), waving.getStatus()))
			.orElseGet(() -> HandWavingStatusResponse.of(NOT_REQUESTED));
	}

	@Transactional
	public void acceptHandWaving(Long userId, Long meetingId, Long handWavingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		HandWaving handWaving = getHandWavingById(handWavingId);
		handWaving.validateIsReceiver(userId);
		handWaving.accept();

		inactiveHandWavingEvent(handWaving);
		publishAcceptedEvent(handWaving);
	}

	@Transactional
	public void ignoreHandWaving(Long userId, Long meetingId, Long handWavingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		HandWaving handWaving = getHandWavingById(handWavingId);
		handWaving.validateIsReceiver(userId);
		handWaving.reject();

		inactiveHandWavingEvent(handWaving);
		publishRejectedEvent(handWaving);
	}

	public List<HandWavingSummary> getHandWavingSummariesBy(List<Event> events) {
		List<Long> eventIds = events.stream()
			.map(Event::getId)
			.toList();

		return handWavingRepository.findAllByEventIdIn(eventIds)
			.stream()
			.map(HandWavingSummary::from)
			.toList();
	}

	private Optional<HandWaving> getHandWavingBySenderIdAndReceiverId(Long senderMemberId, Long receiverMemberId) {
		return handWavingRepository.findFirstBySenderIdAndReceiverIdOrderByIdDesc(senderMemberId, receiverMemberId);
	}

	private HandWaving getHandWavingById(Long handWavingId) {
		return handWavingRepository.findById(handWavingId)
			.orElseThrow(NotFoundHandWavingException::new);
	}

	private void publishRequestedEvent(HandWaving handWaving) {
		MeetingMember sender = handWaving.getSender();
		MeetingMember receiver = handWaving.getReceiver();

		Map<String, String> contentsMap = Map.of(
			"sender", sender.getName(),
			"receiver", receiver.getName()
		);

		Long userId = receiver.getUser().getId();
		Long meetingId = receiver.getMeeting().getId();

		eventService.publishHandWavingEvent(userId, meetingId, EventType.HAND_WAVING_REQUESTED, contentsMap,
			handWaving);
	}

	private void inactiveHandWavingEvent(HandWaving handWaving) {
		Long eventId = handWaving.getEventId();
		if (eventId != null) {
			eventService.inactivate(eventId);
		}
	}

	private void publishAcceptedEvent(HandWaving handWaving) {
		publishAcceptedEventToSender(handWaving);
		publishAcceptedEventToReceiver(handWaving);
	}

	private void publishRejectedEvent(HandWaving handWaving) {
		// 내가 거절했으므로, 거절한 상대방의 이름이 메시지에 포함되어야 함
		Map<String, String> contentsMap = Map.of("member", handWaving.getSender().getName());

		// 이벤트의 대상은 본인이므로, Receiver의 정보를 사용함
		Long userId = handWaving.getReceiver().getUser().getId();
		Long meetingId = handWaving.getReceiver().getMeeting().getId();

		eventService.publish(userId, meetingId, HAND_WAVING_REJECTED, contentsMap);
	}

	private void publishAcceptedEventToSender(HandWaving handWaving) {
		Map<String, String> contentsMap = Map.of("member", handWaving.getReceiver().getName());
		Long userId = handWaving.getSender().getUser().getId();
		Long meetingId = handWaving.getSender().getMeeting().getId();

		eventService.publish(userId, meetingId, HAND_WAVING_ACCEPTED, contentsMap);
	}

	private void publishAcceptedEventToReceiver(HandWaving handWaving) {
		Map<String, String> contentsMap = Map.of("member", handWaving.getSender().getName());
		Long userId = handWaving.getReceiver().getUser().getId();
		Long meetingId = handWaving.getReceiver().getMeeting().getId();

		eventService.publish(userId, meetingId, HAND_WAVING_ACCEPTED, contentsMap);
	}
}
