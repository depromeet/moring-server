package org.depromeet.sambad.moring.meeting.question.presentation;

import java.util.HashSet;
import java.util.Set;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

public enum EventType {
	QUESTION_REGISTERED, TARGET_MEMBER;

	public static Set<EventType> of(MeetingQuestion meetingQuestion, MeetingMember loginMember) {
		Set<EventType> eventTypes = new HashSet<>();
		if (meetingQuestion.getQuestion() != null) {
			eventTypes.add(EventType.QUESTION_REGISTERED);
		}
		if (meetingQuestion.getTargetMember().equals(loginMember)) {
			eventTypes.add(EventType.TARGET_MEMBER);
		}
		return eventTypes;
	}
}
