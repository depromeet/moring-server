package org.depromeet.sambad.moring.domain.meeting.member.infrastructure;

import java.util.List;
import java.util.Random;

import org.depromeet.sambad.moring.domain.meeting.member.application.MeetingMemberRandomGenerator;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;
import org.springframework.stereotype.Service;

@Service
public class RandomMeetingMemberGenerator implements MeetingMemberRandomGenerator {

	@Override
	public MeetingMember generate(List<MeetingMember> nextTargets) {
		Random random = new Random();
		int index = random.nextInt(nextTargets.size());
		return nextTargets.get(index);
	}
}
