package org.depromeet.sambad.moring.meeting.member.application;

import java.util.List;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;

public interface MeetingMemberRandomGenerator {

	MeetingMember generate(List<MeetingMember> nextTargets);
}
