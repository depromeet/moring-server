package org.depromeet.sambad.moring.meeting.member.infrastructure;

import static org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberRole.*;
import static org.depromeet.sambad.moring.meeting.member.domain.QMeetingMember.*;

import java.util.List;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
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

	public boolean isOwnerExceedingMaxMeetings(Long meetingId, int maxHostMeetings) {
		return query.selectFrom(meetingMember)
			.where(meetingMember.meeting.id.eq(meetingId)
				.and(meetingMember.role.eq(OWNER)))
			.fetch().size() >= maxHostMeetings;
	}

	public boolean isUserMemberOfMeeting(Long userId, Long meetingId) {
		return query.selectFrom(meetingMember)
			.where(meetingMember.user.id.eq(userId)
				.and(meetingMember.meeting.id.eq(meetingId)))
			.fetchFirst() != null;
	}

	public List<MeetingMember> findNextTargetsByMeetingId(Long meetingId, Long loginMeetingMemberId,
		List<Long> excludeMemberIds) {
		return query.selectFrom(meetingMember)
			.where(meetingMember.id.ne(loginMeetingMemberId),
				meetingMember.meeting.id.eq(meetingId),
				meetingMember.id.notIn(excludeMemberIds))
			.fetch();
	}
}
