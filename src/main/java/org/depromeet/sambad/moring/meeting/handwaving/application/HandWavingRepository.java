package org.depromeet.sambad.moring.meeting.handwaving.application;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.meeting.handwaving.domain.HandWaving;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;

public interface HandWavingRepository {

	void save(HandWaving handWaving);

	Optional<HandWaving> findById(Long handWavingId);

	Optional<HandWaving> findFirstBySenderIdAndReceiverIdOrderByIdDesc(Long senderMemberId, Long receiverMemberId);

	List<MeetingMember> findHandWavedMembersByMeetingMemberId(Long meetingMemberId);
}
