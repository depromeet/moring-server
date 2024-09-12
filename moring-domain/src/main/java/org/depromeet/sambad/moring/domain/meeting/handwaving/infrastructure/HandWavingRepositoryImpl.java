package org.depromeet.sambad.moring.domain.meeting.handwaving.infrastructure;

import static org.depromeet.sambad.moring.domain.meeting.handwaving.domain.QHandWaving.*;
import static org.depromeet.sambad.moring.domain.meeting.member.domain.QMeetingMember.*;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.domain.meeting.handwaving.application.HandWavingRepository;
import org.depromeet.sambad.moring.domain.meeting.handwaving.domain.HandWavedMemberDto;
import org.depromeet.sambad.moring.domain.meeting.handwaving.domain.HandWaving;
import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
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
	public List<HandWavedMemberDto> findHandWavedMembersByMeetingMemberId(Long meetingMemberId) {
		List<Tuple> results = query.select(handWaving, meetingMember)
			.from(handWaving)
			.join(meetingMember)
			.on(handWaving.receiver.id.eq(meetingMember.id)
				.or(handWaving.sender.id.eq(meetingMember.id)))
			.where(
				handWaving.receiver.id.eq(meetingMemberId)
					.or(handWaving.sender.id.eq(meetingMemberId)),
				meetingMember.id.ne(meetingMemberId)
			)
			.fetch();

		return results.stream()
			.map(tuple -> new HandWavedMemberDto(
				tuple.get(meetingMember),
				tuple.get(handWaving)
			))
			.toList();
	}

	@Override
	public List<HandWaving> findAllByEventIdIn(List<Long> eventIds) {
		return handWavingJpaRepository.findAllByEventIdIn(eventIds);
	}
}
