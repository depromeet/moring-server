package org.depromeet.sambad.moring.meeting.handwaving.infrastructure;

import static org.depromeet.sambad.moring.meeting.handwaving.domain.HandWavingStatus.*;
import static org.depromeet.sambad.moring.meeting.handwaving.domain.QHandWaving.*;
import static org.depromeet.sambad.moring.meeting.member.domain.QMeetingMember.*;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.meeting.handwaving.application.HandWavingRepository;
import org.depromeet.sambad.moring.meeting.handwaving.domain.HandWaving;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HandWavingRepositoryImpl implements HandWavingRepository {

	private final JPAQueryFactory query;
	private final HandWavingJpaRepository handWavingJpaRepository;

	@Override
	public void save(HandWaving handWaving) {
		handWavingJpaRepository.save(handWaving);
	}

	@Override
	public Optional<HandWaving> findById(Long handWavingId) {
		return handWavingJpaRepository.findById(handWavingId);
	}

	@Override
	public Optional<HandWaving> findFirstBySenderIdAndReceiverIdOrderByIdDesc(Long senderMemberId,
		Long receiverMemberId) {
		return handWavingJpaRepository.findFirstBySenderIdAndReceiverIdOrderByIdDesc(senderMemberId, receiverMemberId);
	}

	@Override
	public List<MeetingMember> findHandWavedMembersByMeetingMemberId(Long meetingMemberId) {
		return query.select(meetingMember)
			.from(handWaving)
			.join(meetingMember)
			.on(handWaving.receiver.id.eq(meetingMember.id)
				.or(handWaving.sender.id.eq(meetingMember.id)))
			.where(
				handWaving.receiver.id.eq(meetingMemberId)
					.or(handWaving.sender.id.eq(meetingMemberId)),
				handWaving.status.eq(ACCEPTED),
				meetingMember.id.ne(meetingMemberId)
			)
			.fetch();
	}
}
