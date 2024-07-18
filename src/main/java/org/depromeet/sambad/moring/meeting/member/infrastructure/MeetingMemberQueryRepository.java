package org.depromeet.sambad.moring.meeting.member.infrastructure;

import static org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberType.*;
import static org.depromeet.sambad.moring.meeting.member.domain.QMeetingMember.*;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MeetingMemberQueryRepository {

	private final JPAQueryFactory query;

	public boolean isUserExceedingMaxMeetings(Long userId, int maxMeetings) {
		return query.selectFrom(meetingMember)
			.where(meetingMember.user.id.eq(userId))
			.fetch().size() >= maxMeetings;
	}

	public boolean isMeetingExceedingMaxMembers(Long meetingId, int maxMeetingMembers) {
		return query.selectFrom(meetingMember)
			.where(meetingMember.meeting.id.eq(meetingId))
			.fetch().size() >= maxMeetingMembers;
	}

	public boolean isHostExceedingMaxMeetings(Long meetingId, int maxHostMeetings) {
		return query.selectFrom(meetingMember)
			.where(meetingMember.meeting.id.eq(meetingId)
				.and(meetingMember.type.eq(HOST)))
			.fetch().size() >= maxHostMeetings;
	}
}