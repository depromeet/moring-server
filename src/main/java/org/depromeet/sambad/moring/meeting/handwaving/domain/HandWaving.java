package org.depromeet.sambad.moring.meeting.handwaving.domain;

import static jakarta.persistence.EnumType.STRING;
import static org.depromeet.sambad.moring.meeting.handwaving.domain.HandWavingStatus.REQUESTED;

import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.meeting.handwaving.presentation.exception.InvalidHandWavingReceiverException;
import org.depromeet.sambad.moring.meeting.handwaving.presentation.exception.InvalidHandWavingStatusChangeException;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HandWaving extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hand_waving_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	private MeetingMember sender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id")
	private MeetingMember receiver;

	@Enumerated(STRING)
	private HandWavingStatus status;

	public HandWaving(MeetingMember sender, MeetingMember receiver) {
		this.sender = sender;
		this.receiver = receiver;
		this.status = REQUESTED;
	}

	public static HandWaving send(MeetingMember sender, MeetingMember receiver) {
		return new HandWaving(sender, receiver);
	}

	public void accept() {
		validateStatusIsRequested();
		this.status = HandWavingStatus.ACCEPTED;
	}

	public void reject() {
		validateStatusIsRequested();
		this.status = HandWavingStatus.REJECTED;
	}

	public void validateIsReceiver(Long receiverUserId) {
		if (!this.receiver.getUser().getId().equals(receiverUserId)) {
			throw new InvalidHandWavingReceiverException();
		}
	}

	private void validateStatusIsRequested() {
		if (this.status != REQUESTED) {
			throw new InvalidHandWavingStatusChangeException();
		}
	}
}
