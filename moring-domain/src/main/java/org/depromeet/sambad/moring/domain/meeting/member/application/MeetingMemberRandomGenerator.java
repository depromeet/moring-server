package org.depromeet.sambad.moring.domain.meeting.member.application;

import java.util.List;

import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;

public interface MeetingMemberRandomGenerator {

	MeetingMember generate(List<MeetingMember> nextTargets);
}
